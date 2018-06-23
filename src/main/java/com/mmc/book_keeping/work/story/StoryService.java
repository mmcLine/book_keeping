package com.mmc.book_keeping.work.story;

import com.mmc.book_keeping.work.user.User;
import com.mmc.book_keeping.utils.FileUtil;
import com.mmc.book_keeping.utils.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;
import java.util.List;

@Service
public class StoryService {

    //图片保存的地址
//    private final String filePath = "d:/images/";
    private final String filePath = "/local/images/";

    @Autowired
    private StoryRespository storyRespository;

    /**
     * 新增故事
     *
     * @param file
     * @param story
     * @param user
     */
    public void insStory(MultipartFile file, Story story, User user) {
        if (file != null) {
            try {
                //计算文件md5
                byte[] fileBytes = file.getBytes();
                String fileMD5 = FileUtil.getFileMD5(file.getInputStream());
                //文件目录为filePath+fileMD5
                String dir = filePath + fileMD5 + "/";
                File newDir = new File(dir);
                if (!newDir.exists()) {
                    newDir.mkdirs();
                }
                //写入文件
                File saveFile = new File(dir + file.getOriginalFilename());
                FileOutputStream fos = new FileOutputStream(saveFile);
                fos.write(fileBytes, 0, fileBytes.length);
                fos.flush();
                fos.close();
                file = null;
                //故事
                story.setImgUrl(fileMD5);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        story.setCreateDate(new Date());
        story.setUser(user);
        story.setFamilyer(user.getFamilyer());
        storyRespository.save(story);
    }


    /**
     * 查询故事
     *
     * @return
     */
    public List<Story> listStory() {
        return storyRespository.findAllByFamilyerEquals(SpringContextUtil.getLoginFamiler());
    }


    /**
     * 获取文件输入流
     *
     * @param md5
     * @return
     */
    public FileInputStream getImgInput(String md5) {
        File parentFile = new File(filePath + md5 + "/");
        if (parentFile.exists() && parentFile.isDirectory()) {
            File file = parentFile.listFiles()[0];
            try {
                return new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }else {
            return null;
        }
    }

}
