package life.manong.mycommunity.controller;

import life.manong.mycommunity.mapper.QuestionMapper;
import life.manong.mycommunity.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
            HttpServletRequest request
    ){
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);

        question.setTag(tag);

        questionMapper.create(question);

        return "publish";
    }
}
