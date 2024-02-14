package com.hj.Social.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="board")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {
	
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int board_id;
   
   @Column(nullable = false)
   private String useremail;
   
   @Column(nullable = false)
   private String board_regdate;
   
   @Column(nullable = false)
   private String board_title;
   
   @Column(length = 1000, nullable = false)
   private String board_content;
  
   private String board_moddate;
   private String board_deldate;
   
   @Column(nullable = false)
   @ColumnDefault("'N'")
   private String board_delyn;
   
   @Column(nullable = false)
   @ColumnDefault("0")
   private int board_likes;
   
   @Column(nullable = false)
   @ColumnDefault("0")
   private int board_views;
   

}