/**
 * FileName: FileUtils
 * Author:   嘉平十七
 * Date:     2021/5/12 7:37
 * Description: 文件上传工具类
 */
package com.chen.website.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class FileUtils {

    // 项目根路径下的目录  -- SpringBoot static 目录相当于是根路径下（SpringBoot 默认）
    public final static String BLOG_IMG_PATH = "static/upload/blogPictures";
    public final static String FILE_PATH = "static/upload/files";

    /**
     * 使用此方法，controller中应该如下写：
     * String fileName = System.currentTimeMillis()+"_"+file.getOriginalFilename();
     *         System.out.println("文件名："+fileName);
     *
     *         File fileDir = FileUtils.getImgDirFile();
     *         System.out.println("文件夹绝对路径"+fileDir.getAbsolutePath());
     *
     *         try {
     *             // 构建真实的文件路径
     *             File newFile = new File(fileDir.getAbsolutePath() + File.separator + fileName);
     *             System.out.println(newFile.getAbsolutePath());
     *
     *             // 上传图片到“绝对路径”
     *             file.transferTo(newFile);
     *             information.setFile(newFile.getAbsolutePath());
     *         } catch (IOException e) {
     *             e.printStackTrace();
     *         }
     * 这里提出工具方法：uploadFile
     * @return
     */
    public static File getImgDirFile(){
        // 构建上传文件的存放 "文件夹" 路径
        String fileDirPath = "src/main/resources/" + BLOG_IMG_PATH;

        File fileDir = new File(fileDirPath);
        if(!fileDir.exists()){
            // 递归生成文件夹
            fileDir.mkdirs();
        }
        return fileDir;
    }

    public static String uploadFile(String type, MultipartFile file) throws IOException {
        System.err.println("Utils");
        //创建文件夹
        String fileDirPath;
        String filePath;
        if (type.equals("blogPicture")) {
            fileDirPath = "src/main/resources/" + BLOG_IMG_PATH;
            filePath = "/upload/blogPictures/";
        }else {
            fileDirPath = "src/main/resources/" + FILE_PATH;
            filePath = "/upload/files/";
        }
        File fileDir = new File(fileDirPath);
        if (!fileDir.exists()) fileDir.mkdirs();
        //获取文件名
        String fileName = System.currentTimeMillis()+"_"+file.getOriginalFilename();
        File newFile = new File(fileDir.getAbsolutePath()+File.separator+fileName);
        file.transferTo(newFile);
        //因为要设置数据库中的file字段，所以返回一个文件路径
        return filePath + fileName;
    }

    public static Boolean downloadFile(HttpServletResponse response, String path){
        File file = new File(path);
        String fileName = path.substring(14);//文件路径前缀长度固定
        if (file.exists()){
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName="+fileName);// 设置文件名
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }

    public static String deleteFile(String path){
        File file = new File(path);
        if (file.exists()){
            file.delete();
            return "删除成功";
        }else {
            return "文件不存在";
        }
    }
}