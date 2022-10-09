package life.manong.mycommunity.controller;

import life.manong.mycommunity.mapper.QuestionMapper;
import life.manong.mycommunity.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("/publish")
    public String publish(){

        return "publish";
    }

    @PostMapping ("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model

    ){
        //将数据设置到域中，便于回显
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        /**
         * 进行验证如果已经登陆，则可以进行发布
         * 但是我的账号被封禁，所以暂时无法实现
         */

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(System.currentTimeMillis());
        question.setCreator(123456);

        questionMapper.create(question);

        return "publish";
    }
}
