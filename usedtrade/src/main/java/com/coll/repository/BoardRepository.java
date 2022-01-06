package com.coll.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coll.model.Board;

public interface BoardRepository extends JpaRepository<Board, Integer>{

}
