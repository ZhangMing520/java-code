package com.example.unrar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import net.sf.sevenzipjbinding.ExtractAskMode;
import net.sf.sevenzipjbinding.ExtractOperationResult;
import net.sf.sevenzipjbinding.IArchiveExtractCallback;
import net.sf.sevenzipjbinding.ISequentialOutStream;
import net.sf.sevenzipjbinding.IInArchive;
import net.sf.sevenzipjbinding.PropID;
import net.sf.sevenzipjbinding.SevenZip;
import net.sf.sevenzipjbinding.SevenZipException;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;

/**
 * @author zhangming
 * @date 2022/11/24 21:09
 *
 * <a href="https://gist.github.com/borisbrodski/6120309">borisbrodski作者</a>
 */
public class ExtractExample {
    static class ExtractionException extends Exception {
        private static final long serialVersionUID = -5108931481040742838L;

        ExtractionException(String msg) {
            super(msg);
        }

        public ExtractionException(String msg, Exception e) {
            super(msg, e);
        }
    }

    class ExtractCallback implements IArchiveExtractCallback {
        private final IInArchive inArchive;
        private int index;
        private OutputStream outputStream;
        private File file;
        private ExtractAskMode extractAskMode;
        private boolean isFolder;

        ExtractCallback(IInArchive inArchive) {
            this.inArchive = inArchive;
        }

        @Override
        public void setTotal(long total) throws SevenZipException {

        }

        @Override
        public void setCompleted(long completeValue) throws SevenZipException {

        }

        @Override
        public ISequentialOutStream getStream(int index,
                                              ExtractAskMode extractAskMode) throws SevenZipException {
            closeOutputStream();

            this.index = index;
            this.extractAskMode = extractAskMode;
            this.isFolder = (Boolean) inArchive.getProperty(index,
                    PropID.IS_FOLDER);

            if (extractAskMode != ExtractAskMode.EXTRACT) {
                // Skipped files or files being tested
                return null;
            }

            String path = (String) inArchive.getProperty(index, PropID.PATH);
            file = new File(outputDirectoryFile, path);
            if (isFolder) {
                createDirectory(file);
                return null;
            }

            createDirectory(file.getParentFile());

            try {
                outputStream = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                throw new SevenZipException("Error opening file: "
                        + file.getAbsolutePath(), e);
            }

            return new ISequentialOutStream() {
                public int write(byte[] data) throws SevenZipException {
                    try {
                        outputStream.write(data);
                    } catch (IOException e) {
                        throw new SevenZipException("Error writing to file: "
                                + file.getAbsolutePath());
                    }
                    return data.length; // Return amount of consumed data
                }
            };
        }

        private void createDirectory(File parentFile) throws SevenZipException {
            if (!parentFile.exists()) {
                if (!parentFile.mkdirs()) {
                    throw new SevenZipException("Error creating directory: "
                            + parentFile.getAbsolutePath());
                }
            }
        }

        private void closeOutputStream() throws SevenZipException {
            if (outputStream != null) {
                try {
                    outputStream.close();
                    outputStream = null;
                } catch (IOException e) {
                    throw new SevenZipException("Error closing file: "
                            + file.getAbsolutePath());
                }
            }
        }

        @Override
        public void prepareOperation(ExtractAskMode extractAskMode)
                throws SevenZipException {

        }

        @Override
        public void setOperationResult(
                ExtractOperationResult extractOperationResult)
                throws SevenZipException {
            closeOutputStream();
            String path = (String) inArchive.getProperty(index, PropID.PATH);
            if (extractOperationResult != ExtractOperationResult.OK) {
                throw new SevenZipException("Invalid file: " + path);
            }

            if (!isFolder) {
                switch (extractAskMode) {
                    case EXTRACT:
                        System.out.println("Extracted " + path);
                        break;
                    case TEST:
                        System.out.println("Tested " + path);

                    default:
                }
            }
        }

    }

    private final String archive;
    private final String outputDirectory;
    private File outputDirectoryFile;
    private final boolean test;
    private final String filterRegex;

    ExtractExample(String archive, String outputDirectory, boolean test, String filter) {
        this.archive = archive;
        this.outputDirectory = outputDirectory;
        this.test = test;
        this.filterRegex = filterToRegex(filter);
    }

    void extract() throws ExtractionException {
        checkArchiveFile();
        prepareOutputDirectory();
        extractArchive();
    }

    private void prepareOutputDirectory() throws ExtractionException {
        outputDirectoryFile = new File(outputDirectory);
        if (!outputDirectoryFile.exists()) {
            outputDirectoryFile.mkdirs();
        } else {
            if (outputDirectoryFile.list().length != 0) {
                throw new ExtractionException("Output directory not empty: "
                        + outputDirectory);
            }
        }
    }

    private void checkArchiveFile() throws ExtractionException {
        if (!new File(archive).exists()) {
            throw new ExtractionException("Archive file not found: " + archive);
        }
        if (!new File(archive).canRead()) {
            System.out.println("Can't read archive file: " + archive);
        }
    }

    public void extractArchive() throws ExtractionException {
        RandomAccessFile randomAccessFile;
        boolean ok = false;
        try {
            randomAccessFile = new RandomAccessFile(archive, "r");
        } catch (FileNotFoundException e) {
            throw new ExtractionException("File not found", e);
        }
        try {
            extractArchive(randomAccessFile);
            ok = true;
        } finally {
            try {
                randomAccessFile.close();
            } catch (Exception e) {
                if (ok) {
                    throw new ExtractionException("Error closing archive file",
                            e);
                }
            }
        }
    }

    private static String filterToRegex(String filter) {
        if (filter == null) {
            return null;
        }
        return "\\Q" + filter.replace("*", "\\E.*\\Q") + "\\E";
    }

    private void extractArchive(RandomAccessFile file)
            throws ExtractionException {
        IInArchive inArchive;
        boolean ok = false;
        try {
            inArchive = SevenZip.openInArchive(null,
                    new RandomAccessFileInStream(file));
        } catch (SevenZipException e) {
            throw new ExtractionException("Error opening archive", e);
        }
        try {

            int[] ids = null; // All items
            if (filterRegex != null) {
                ids = filterIds(inArchive, filterRegex);
            }
            inArchive.extract(ids, test, new ExtractCallback(inArchive));
            ok = true;
        } catch (SevenZipException e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error extracting archive '");
            stringBuilder.append(archive);
            stringBuilder.append("': ");
            stringBuilder.append(e.getMessage());
            if (e.getCause() != null) {
                stringBuilder.append(" (");
                stringBuilder.append(e.getCause().getMessage());
                stringBuilder.append(')');
            }
            String message = stringBuilder.toString();

            throw new ExtractionException(message, e);
        } finally {
            try {
                inArchive.close();
            } catch (SevenZipException e) {
                if (ok) {
                    throw new ExtractionException("Error closing archive", e);
                }
            }
        }
    }

    private static int[] filterIds(IInArchive inArchive, String regex) throws SevenZipException {
        List<Integer> idList = new ArrayList<Integer>();

        int numberOfItems = inArchive.getNumberOfItems();

        Pattern pattern = Pattern.compile(regex);
        for (int i = 0; i < numberOfItems; i++) {
            String path = (String) inArchive.getProperty(i, PropID.PATH);
            String fileName = new File(path).getName();
            if (pattern.matcher(fileName).matches()) {
                idList.add(i);
            }
        }

        int[] result = new int[idList.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = idList.get(i);
        }
        return result;
    }

    public static void main(String[] args) {
        boolean test = false;
        String filter = null;
        List<String> argList = new ArrayList<String>(Arrays.asList(args));
        if (argList.size() > 0 && argList.get(0).equals("-t")) {
            argList.remove(0);
            test = true;
        }
        if (argList.size() != 2 && argList.size() != 3) {
            System.out.println("Usage:");
            System.out
                    .println("java -cp ... example.ExtractExample [-t] <archive> <output-dir> [filter]");
            System.exit(1);
        }
        if (argList.size() == 3) {
            filter = argList.get(2);
        }
        try {
            new ExtractExample(argList.get(0), argList.get(1), test, filter).extract();
            System.out.println("Extraction successfull");
        } catch (ExtractionException e) {
            System.err.println("ERROR: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
}