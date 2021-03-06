package com.estsoft.mysite.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.estsoft.db.DBConnection;
import com.estsoft.db.DBUtils;
import com.estsoft.mysite.vo.UserVo;

public class UserDao {
	private DBConnection dbConnection;
	
	public UserDao( DBConnection dbConnection ) {
		this.dbConnection = dbConnection;
	}
	

	public UserVo get( Long userNo ) {
		UserVo userVo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dbConnection.getConnection();
			String sql = "SELECT no, name, email, gender FROM user WHERE no=?"; 
			pstmt = conn.prepareStatement( sql );
			pstmt.setLong( 1, userNo );

			rs = pstmt.executeQuery();
			if( rs.next() ) {
				Long no = rs.getLong( 1 );
				String name = rs.getString( 2 );
				String email = rs.getString( 3 );
				String gender = rs.getString( 4 );
				
				userVo = new UserVo();
				userVo.setNo(no);
				userVo.setName(name);
				userVo.setEmail(email);
				userVo.setGender(gender);
			}
			return userVo;
		} catch (SQLException e) {
			System.out.println( "error:" + e );
			return null;
		} finally {
			DBUtils.clean_up(conn, pstmt, rs);
		}
	}	

	public UserVo get( String email, String password ) {
		UserVo vo = new UserVo();
		vo.setEmail(email);
		vo.setPassword(password);
		vo = get(vo);
		return vo;
	}
	
	public UserVo get( UserVo vo ) {
		UserVo userVo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dbConnection.getConnection();
			String sql = "SELECT no, name, email FROM user WHERE email=? AND passwd=password(?)"; 
			pstmt = conn.prepareStatement( sql );
			pstmt.setString( 1, vo.getEmail() );
			pstmt.setString( 2, vo.getPassword() );

			rs = pstmt.executeQuery();
			if( rs.next() ) {
				Long no = rs.getLong( 1 );
				String name = rs.getString( 2 );
				String email = rs.getString( 3 );
				userVo = new UserVo();
				userVo.setNo(no);
				userVo.setName(name);
				userVo.setEmail(email);
			}
			
			return userVo;
		} catch (SQLException e) {
			System.out.println( "error:" + e );
			return null;
		} finally {
			DBUtils.clean_up(conn, pstmt, rs);
		}
	}

	public void update( UserVo userVo ) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbConnection.getConnection();
			if( "".equals( userVo.getPassword() ) ) {
				String sql = "UPDATE user SET  name=?, gender=?, email=? WHERE no = ?";
				pstmt = conn.prepareStatement( sql );
				
				pstmt.setString( 1, userVo.getName() );
				pstmt.setString( 2,  userVo.getGender() );
				pstmt.setString( 3,  userVo.getEmail() );
				pstmt.setLong( 4,  userVo.getNo() );
			} else {
				String sql = "UPDATE user SET  name=?, gender=?, email=?, passwd=password(?) WHERE no = ?";	
				pstmt = conn.prepareStatement( sql );
				pstmt.setString( 1, userVo.getName() );
				pstmt.setString( 2,  userVo.getGender() );
				pstmt.setString( 3,  userVo.getEmail() );
				pstmt.setString( 4,  userVo.getPassword() );
				pstmt.setLong( 5,  userVo.getNo() );
			}
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println( "error:" + e );	
		} finally {
			DBUtils.clean_up(conn, pstmt);
		}
	}
	
	public void insert( UserVo vo ) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbConnection.getConnection();
			String sql = 
	"INSERT INTO user VALUES (null, ?, ?, password(?), ? )";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString( 1, vo.getName() );
			pstmt.setString( 2, vo.getEmail() );
			pstmt.setString( 3, vo.getPassword() );
			pstmt.setString( 4,  vo.getGender() );
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println( "error:" + e );	
		} finally {
			DBUtils.clean_up(conn, pstmt);
		}
	}
	
	public String getUserName( Long userNo ) {
		String name = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dbConnection.getConnection();
			String sql = "SELECT name FROM user WHERE no=?"; 
			pstmt = conn.prepareStatement( sql );
			pstmt.setLong( 1, userNo );

			rs = pstmt.executeQuery();
			if( rs.next() ) {
				name = rs.getString( 1 );
			}
			return name;
		} catch (SQLException e) {
			System.out.println( "error:" + e );
			return null;
		} finally {
			DBUtils.clean_up(conn, pstmt, rs);
		}
	}	
}
