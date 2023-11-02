package com.fastcampus.ch4.service;

import com.fastcampus.ch4.dao.*;
import com.fastcampus.ch4.domain.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    BoardDao boardDao;
    @Autowired
    CommentDao commentDao;

    //    @Autowired
    //    public CommentServiceImpl(CommentDao commentDao, BoardDao boardDao) {
    //        this.commentDao = commentDao;
    //        this.boardDao = boardDao;
    //    }

    @Override
    public int getCount(Integer bno) throws Exception {
        return commentDao.count(bno);
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // 예외가 발생하면 롤백
    public int remove(Integer cno, Integer bno, String commenter) throws Exception {
        // 게시판의 댓글 갯수 하나 줄음
        int rowCnt = boardDao.updateCommentCnt(-1, bno);
        System.out.println("updateCommentCnt - rowCnt = " + rowCnt);
        // throw new Exception("test") // 예외가 잘 뜨는지 확인

        // 댓글 하나 삭제
        rowCnt = commentDao.delete(cno, commenter);
        System.out.println("rowCnt = " + rowCnt);

        return rowCnt;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int write(CommentDto commentDto) throws Exception {
        boardDao.updateCommentCnt(commentDto.getBno(), 1);
        //                throw new Exception("test");
        return commentDao.insert(commentDto);
    }

    @Override
    public List<CommentDto> getList(Integer bno) throws Exception {
        //        throw new Exception("test");
        return commentDao.selectAll(bno);
    }

    @Override
    public CommentDto read(Integer cno) throws Exception {
        return commentDao.select(cno);
    }

    @Override
    public int modify(CommentDto commentDto) throws Exception {
        return commentDao.update(commentDto);
    }
}