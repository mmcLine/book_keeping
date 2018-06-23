package com.mmc.book_keeping.work.story;

import com.mmc.book_keeping.utils.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Controller
public class StoryController {

    @Autowired
    private StoryService storyService;

    @RequestMapping("book/story/instory")
    public String insStory(MultipartFile file, Story story){
        storyService.insStory(file, story, SpringContextUtil.getLoginUser().getUser());
        return "redirect:/story_list.html";
    }

    @RequestMapping("book/story/listStory")
    @ResponseBody
    public List<Story> listStory(){
        return storyService.listStory();
    }


    //前端读取图片
    @RequestMapping("/book/sotry/getImag/{ImgMd5}")
    public void getImg(HttpServletResponse response,@NotNull @PathVariable String ImgMd5){
            FileInputStream is =storyService.getImgInput(ImgMd5);
            if (is != null){
                try(OutputStream outputStream = response.getOutputStream();) {
                   int i = is.available();
                   byte data[] = new byte[i];
                    is.read(data); // 读数据
                    is.close();
                    response.setContentType("image/jpeg");  // 设置返回的文件类型
                    outputStream.write(data); // 输出数据
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

}
