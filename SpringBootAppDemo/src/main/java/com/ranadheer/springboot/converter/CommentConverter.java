package com.ranadheer.springboot.converter;

import com.ranadheer.springboot.dto.CommentDTO;
import com.ranadheer.springboot.entity.Comment;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentConverter {

    public CommentDTO entityToDto(Comment comment)
    {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setComment(comment.getContent());
        commentDTO.setId(comment.getId());
        commentDTO.setUserId(comment.getUserId());
        commentDTO.setArticleId(comment.getArticleId());
        return commentDTO;
    }

    public Comment dtoToEntity(CommentDTO commentDTO)
    {
        Comment comment = new Comment();
        comment.setUserId(commentDTO.getUserId());
        comment.setContent(commentDTO.getComment());
        comment.setArticleId(commentDTO.getArticleId());
        comment.setId(commentDTO.getId());
        return comment;
    }

    public List<CommentDTO> entityToDto(List<Comment> comments)
    {
        return comments.stream().map(x->entityToDto(x)).collect(Collectors.toList());
    }

    public List<Comment> dtoToEntity(List<CommentDTO> commentDTOS )
    {
        return commentDTOS.stream().map(x->dtoToEntity(x)).collect(Collectors.toList());
    }

}
