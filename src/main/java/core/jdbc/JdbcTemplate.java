package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;

public class JdbcTemplate {
	 

	  
	  public void update(String sql,PreparedStatementSetter pss) throws DateAccessException {
	    	 
	         try(Connection  con = ConnectionManager.getConnection();
	        		 PreparedStatement	 pstmt = con.prepareStatement(sql);
	        		 )    {
	           
	             pss.setParameters(pstmt);
	             pstmt.executeUpdate();
	             
	         } catch (SQLException e) {
				throw new DateAccessException(e);
			} 
	    }
	  public void update(PreparedStatementCreator psc,KeyHolder holder) throws DateAccessException {
	    	 
	         try(Connection  con = ConnectionManager.getConnection())    {

        		 PreparedStatement	 pstmt = psc.createPreparedStatement(con);
	             pstmt.executeUpdate();
	             
	             ResultSet rs=pstmt.getGeneratedKeys();
	             if(rs.next()) {
	            	 holder.setId(rs.getLong(1));
	             }
	             rs.close();
	         } catch (SQLException e) {
				throw new DateAccessException(e);
			} 
	    }
	  
	  public void update(String sql,Object... parameters)throws DateAccessException {
	    	 
	        update(sql,createPreparedStatementSetter(parameters));
	    }
	  
		public <T> List<T> query(String sql,PreparedStatementSetter pss,RowMapper<T> rm) throws DateAccessException{
		  ResultSet rs = null;
			try(Connection con= ConnectionManager.getConnection();
					PreparedStatement pstmt = con.prepareStatement(sql)){

				pss.setParameters(pstmt);
				
				rs = pstmt.executeQuery();
				
				List<T> result = new ArrayList<T>();
				while(rs.next()){
					result.add(rm.mapRow(rs));
				}
				
				return result;
			} catch (SQLException e) {
				throw new DateAccessException(e);
			}finally {
				if(rs!=null) {
					try {
						rs.close();
					} catch (SQLException e) {
						throw new DateAccessException(e);
					}
				} 
			}
		}
		public <T> List<T> query(String sql,RowMapper<T> rm,Object... parameters) throws DateAccessException{
			 return query(sql,createPreparedStatementSetter(parameters),rm);
			}
	
		public <T> T queryForObject(String sql,PreparedStatementSetter pss,RowMapper<T> rm) throws DateAccessException{
			List<T> result = query(sql,pss,rm);
			if(result.isEmpty()) {
				return null;
			}else {
				return result.get(0);
			}
		}
		public <T> T queryForObject(String sql,RowMapper<T> rm,Object... parameters) throws DateAccessException{
			return queryForObject(sql,createPreparedStatementSetter(parameters),rm);
		}
		private PreparedStatementSetter createPreparedStatementSetter(Object...parameters) {
			return new PreparedStatementSetter() {
				public void setParameters(PreparedStatement pstmt)throws SQLException{
					 for(int i=0;i<parameters.length;i++) {
		        		 pstmt.setObject(i+1,parameters[i]);
		        	 }
				}
			};
		}
}