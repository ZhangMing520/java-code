package com.example.unrar;

import com.github.junrar.Junrar;
import com.github.junrar.exception.RarException;

import java.io.IOException;

/**
 * @author zhangming
 * @date 2022/11/24 21:01
 * <p>
 * 不支持RAR5.0版本
 * Support for rar version 5 is not yet implemented!
 */
public class JunrarTest {
    public static void main(String[] args) throws RarException, IOException {
        String rarPath = "C:\\Users\\zhangming\\Downloads\\UnRARDLL.rar";
        String destPath = "C:\\Users\\zhangming\\Downloads\\";
        Junrar.extract(rarPath, destPath);
    }
}
