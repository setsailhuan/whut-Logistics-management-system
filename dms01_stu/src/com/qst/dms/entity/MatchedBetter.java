package com.qst.dms.entity;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.qst.dms.db.DBUtil;


public class MatchedBetter extends AbstractTableModel{
private Vector<String[]> TableData;//用来存放表格数据的线性表
//表格的列标题        
//标志位，区分日志和物流：1，日志；0，物流
private int sign;
public MatchedBetter(int sign) {
	this.sign=sign;
	if(sign==1) {
		 AddFromDB();
	}
	else {
		AddTransFromDB();
	}
}
//获取表格行数
	public int getRowCount() {
		try {
			 
			// System.out.println(count);
			return TableData.size();
		} catch (Exception e) {
			return 0;
		}
	}

	// 获取表格的列数
	public int getColumnCount() {
		try {
			// System.out.println(rsmd.getColumnCount());
			if(sign==1)
				return 4;
			else
				return 5;
		} catch (Exception e) {
			return 0;
		}
	}

	// 获取指定位置的值
	 public Object getValueAt(int rowIndex, int columnIndex)
    {
        //获取每一行对应的String[]数组
        String LineTemp[] = (String[])this.TableData.get(rowIndex);
        //提取出对 应的数据
        return LineTemp[columnIndex];
    }

	// 获取表头信息
	public String getColumnName(int column) {
		String[] logArray = { "用户名","登录时间","登出时间","登录地点"};//"日志类型" 
		String[] tranArray = { "采集时间", "目的地", "经手人", "收货人","物流类型"};//,//"物流类型" };
		return sign == 1 ? logArray[column] : tranArray[column];
	}
	
	public void AddFromDB() {
		DBUtil db = new DBUtil();
		TableData = new Vector<String[]>();
		try {
			//获取数据库链接
			db.getConnection();//查询匹配的日志
			String sql = "SELECT i.id,i.time,i.address,i.type,i.username,i.ip,i.logtype,"
					+ "o.id,o.time,o.address,o.type,o.username,o.ip,o.logtype "
					+"FROM matched_logrec m,gather_logrec i,gather_logrec o "
					+"WHERE m.loginid=i.id AND m.logoutid=o.id";
			ResultSet rs = db.executeQuery(sql, null);
				while (rs.next()) {
					//获取登录记录
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					String dateStr = sdf.format(rs.getTimestamp(2));
					//System.out.print(rs.getTimestamp(2));
					String dateStrout =sdf.format(rs.getTimestamp(9));
					String []Line0 = {rs.getString(5),dateStr,dateStrout,rs.getString(3)};

					 //将数据挂到线性表形成二维的数据表，形成映射
					 TableData.add(Line0);  
					/*LogRec login = new LogRec(rs.getInt(1),rs.getDate(2),
							rs.getString(3), rs.getInt(4),rs.getString(5),
							rs.getString(6),rs.getInt(7));
					//获取登出记录
					LogRec logout = new LogRec(rs.getInt(8),rs.getDate(9),
							rs.getString(10), rs.getInt(11),rs.getString(12),
							rs.getString(13), rs.getInt(14));*/
					//添加匹配登录信息到匹配集合
					
			}
				//关闭数据库连接,释放资源
				db.closeAll();
			}catch (Exception e) {
				e.printStackTrace();
			}

		}
	
	
	public void AddTransFromDB(){
		DBUtil db = new DBUtil();
		TableData = new Vector<String[]>();
		try {
			//获取教据库链接
			db.getConnection() ;
			//查询匹配的物流
			String sql = "SELECT s.ID,s.TIME,s.ADDRESS,s.TYPE,s.HANDLER,s.RECIVER,s.TRANSPORTTYPE,"
				+ "t.ID,t.TIME,t.ADDRESS,t.TYPE,t.HANDLER,t.RECIVER,t.TRANSPORTTYPE,"
				+ "r.ID,r.TIME,r.ADDRESS,r.TYPE,r.HANDLER,r.RECIVER,r.TRANSPORTTYPE "
				+ "FROM MATCHED_TRANSPORT m,GATHER_TRANSPORT s,GATHER_TRANSPORT t ,GATHER_TRANSPORT r "
				+ "WHERE m.SENDID=s.ID AND m.TRANSID=t.ID AND m.RECEIVEID=r.ID";
			ResultSet rs = db.executeQuery(sql, null);
			while (rs.next()){
				
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String dateStr1 = sdf.format(rs.getTimestamp(2));
				String dateStr2 = sdf.format(rs.getTimestamp(9));
				String dateStr3 = sdf.format(rs.getTimestamp(16));
				String a,b,c;
				a=getTypeTrans(rs.getInt(7));
				b=getTypeTrans(rs.getInt(14));
				c=getTypeTrans(rs.getInt(21));
				String []Line1 = {dateStr1,rs.getString(3), rs.getString(5),rs.getString(6), a};
				
				String []Line2= {dateStr2,rs.getString( 10), rs.getString(12),rs.getString(13),b };
				String []Line3= {dateStr3,rs.getString(17), rs.getString(19),rs.getString(20),c};
				
				TableData.add(Line1);
				TableData.add(Line2);
				TableData.add(Line3);
				
				
				/*
				//获取发送记录
				Transport send = new Transport(rs.getInt(1),rs.getDate(2),
						rs.getString(3), rs.getInt(4), rs.getString(5),
						rs.getString(6), rs.getInt(7));
				//获取运输记录
				Transport trans = new Transport(rs.getInt(8), rs.getDate(9),
						rs.getString( 10), rs.getInt(11), rs.getString(12),
						rs.getString(13), rs.getInt(14));
				//获取接收记录
				Transport receive = new Transport(rs.getInt(15),
						rs.getDate(16),rs.getString(17), rs.getInt(18),
						rs.getString(19),rs.getString(20),rs.getInt(21));
				//添加匹配登录信息到匹配集合
				 * 
				 */
			}
			//关闭教据库连接,释放资源
			db.closeAll();
		}catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public String getTypeTrans(int n) {
		
		String str=null;
		if(n==1) {
			str="发货中";
		}
		else if(n==2)
		{
			str="送货中";
		}
		else
			str="已签收";
		return str;
	}
	
}

