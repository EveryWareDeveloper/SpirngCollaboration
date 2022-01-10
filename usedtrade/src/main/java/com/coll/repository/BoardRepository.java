package com.coll.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.coll.model.Board;

public interface BoardRepository extends JpaRepository<Board, Integer>{

	Page<Board> findAll(Pageable pageable);
	Page<Board> findByContentContaining(String content, Pageable pageable);
	Page<Board> findByTitleContaining(String title, Pageable pageable);


}
