package com.synergies.synergyv2.service;

import com.synergies.synergyv2.model.dto.AssignmentResponseDto;
import com.synergies.synergyv2.model.entity.AssignmentSubmitEntity;
import com.synergies.synergyv2.model.entity.CommentEntity;
import com.synergies.synergyv2.repository.AssignmentRepository;
import com.synergies.synergyv2.repository.AssignmentSubmitRepository;
import com.synergies.synergyv2.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final AssignmentSubmitRepository submitRepository;
    private final AssignmentRepository assignmentRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void createComment(int id, String content) {
        AssignmentSubmitEntity submit = submitRepository.findById(id).get();
        CommentEntity comment = CommentEntity.builder()
                .assignmentSubmit(submit)
                .content(content)
                .build();
        commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(int id) {
        commentRepository.deleteById(id);
    }

    public List<AssignmentResponseDto.CommentList> getComment(int id) {
        List<CommentEntity> commentList = commentRepository.findAllByAssignmentSubmit_Id(id);
        List<AssignmentResponseDto.CommentList> comments = new ArrayList<>();

        for(CommentEntity comment : commentList) {
            comments.add(AssignmentResponseDto.CommentList.builder()
                            .id(comment.getId())
                            .content(comment.getContent())
                            .regDate(comment.getRegDate().toString())
                            .build());
        }

        return comments;
    }
}
