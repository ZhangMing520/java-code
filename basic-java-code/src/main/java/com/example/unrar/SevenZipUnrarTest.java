package com.example.unrar;

import net.sf.sevenzipjbinding.*;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;

import java.io.*;

/**
 * @author zhangming
 * @date 2022/11/24 21:09
 *
 * <a href="https://www.jianshu.com/p/542363811285">简书：java解压缩zip、rar</a>
 * <a href="https://www.cnblogs.com/fan93/p/15016717.html">Blog：Java 解压RAR5</a>
 * <a href="https://blog.csdn.net/qq974816077/article/details/115384443">CSDN：java解压rar5 兼容rar4</a>
 * <a href="https://github.com/junrar/junrar">github junrar</a>
 *
 * <a href="https://www.rarlab.com/rar_add.htm">rar官网命令行工具</a>
 * <p>
 * 7-Zip-JBinding is a free cross-platform java binding of 7-Zip free compress/decompress library
 */
public class SevenZipUnrarTest {

    public static void main(String[] args) throws IOException, ExtractionException {
        String rarPath = "C:\\Users\\zhangming\\Downloads\\神码平台-O32数据库切换到10.202.0.202影响清单.rar";
        String destPath = "C:\\Users\\zhangming\\Downloads\\";

        new SevenZipUnrarTest(rarPath, destPath).extract();
    }

    private String archive;
    private String outputDirectory;
    private File outputDirectoryFile;

    public SevenZipUnrarTest(String archive, String outputDirectory) {
        this.archive = archive;
        this.outputDirectory = outputDirectory;
    }

    void extract() throws ExtractionException {
        checkArchiveFile();
        prepareOutputDirectory();
        extractArchive();
    }


    private void checkArchiveFile() throws ExtractionException {
        if (!new File(archive).exists()) {
            throw new ExtractionException("Archive file not found: " + archive);
        }
        if (!new File(archive).canRead()) {
            System.out.println("Can't read archive file: " + archive);
        }
    }

    private void prepareOutputDirectory() throws ExtractionException {
        outputDirectoryFile = new File(outputDirectory);
        if (!outputDirectoryFile.exists()) {
            outputDirectoryFile.mkdirs();
        } else {
//            if (outputDirectoryFile.list().length != 0) {
//                throw new ExtractionException("Output directory not empty: " + outputDirectory);
//            }
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
                    throw new ExtractionException("Error closing archive file", e);
                }
            }
        }
    }

    private void extractArchive(RandomAccessFile file)
            throws ExtractionException {
        IInArchive inArchive;
        boolean ok = false;
        boolean test = false;
        try {
            inArchive = SevenZip.openInArchive(ArchiveFormat.RAR5, new RandomAccessFileInStream(file));
        } catch (SevenZipException e) {
            throw new ExtractionException("Error opening archive", e);
        }
        try {

            int[] ids = new int[inArchive.getNumberOfItems()];
            for (int i = 0; i < ids.length; i++) {
                ids[i] = i;
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

    class ExtractCallback implements IArchiveExtractCallback {
        private IInArchive inArchive;
        private int index;
        private OutputStream outputStream;
        private File file;
        private ExtractAskMode extractAskMode;
        private boolean isFolder;


        public ExtractCallback(IInArchive inArchive) {
            this.inArchive = inArchive;
        }


        @Override
        public void setTotal(long total) throws SevenZipException {
        }

        @Override
        public void setCompleted(long completed) throws SevenZipException {
        }

        /***
         * Return sequential output stream for the file with index index.
         *
         * @param index             index of the item to extract
         * @param extractAskMode    EXTRACT/TEST/SKIP
         * @return an instance of ISequentialOutStream sequential out stream or null to skip the extraction of the current item (with index index) and proceed with the next one
         * @throws SevenZipException
         */
        @Override
        public ISequentialOutStream getStream(int index, ExtractAskMode extractAskMode) throws SevenZipException {
            closeOutputStream();

            this.index = index;
            this.extractAskMode = extractAskMode;
            this.isFolder = (boolean) inArchive.getProperty(index, PropID.IS_FOLDER);
            if (extractAskMode != ExtractAskMode.EXTRACT) {
                // Skipped files or files being tested
                return null;
            }
            // 这里的path是以压缩包为起始目录，test/UnRARDLL.exe
            final String path = (String) inArchive.getProperty(index, PropID.PATH);
            file = new File(outputDirectoryFile, path);
            if (isFolder) {
                createDirectory(file);
                return null;
            }
            createDirectory(file.getParentFile());
            try {
                outputStream = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                throw new SevenZipException("Error opening file: " + file.getAbsolutePath(), e);
            }
            return new ISequentialOutStream() {
                public int write(byte[] data) throws SevenZipException {
                    try {
                        outputStream.write(data);
                    } catch (IOException e) {
                        throw new SevenZipException("Error writing to file: " + file.getAbsolutePath());
                    }
                    return data.length; // Return amount of consumed data
                }
            };
        }

        /***
         * The index of the current archive item can be taken from the last call of getStream(int, ExtractAskMode).
         * @param extractAskMode
         * @throws SevenZipException
         */
        @Override
        public void prepareOperation(ExtractAskMode extractAskMode) throws SevenZipException {
        }

        /***
         * Set result of extraction operation of the file with index index from last call of getStream(int, ExtractAskMode).
         *
         * @param extractOperationResult
         * @throws SevenZipException
         */
        @Override
        public void setOperationResult(ExtractOperationResult extractOperationResult) throws SevenZipException {
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


        private void createDirectory(File parentFile) throws SevenZipException {
            if (!parentFile.exists()) {
                if (!parentFile.mkdirs()) {
                    throw new SevenZipException("Error creating directory: " + parentFile.getAbsolutePath());
                }
            }
        }

        private void closeOutputStream() throws SevenZipException {
            if (outputStream != null) {
                try {
                    outputStream.close();
                    outputStream = null;
                } catch (IOException e) {
                    throw new SevenZipException("Error closing file: " + file.getAbsolutePath());
                }
            }
        }
    }


    static class ExtractionException extends Exception {
        private static final long serialVersionUID = -5108931481040742838L;

        ExtractionException(String msg) {
            super(msg);
        }

        public ExtractionException(String msg, Exception e) {
            super(msg, e);
        }
    }

}
