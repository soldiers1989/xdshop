package com.pujjr.gps.service.impl;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetMetaData;
import com.pujjr.gps.dal.dao.HisBeanMapMapper;
import com.pujjr.gps.dal.dao.HisFieldCommentMapper;
import com.pujjr.gps.dal.domain.HisBeanMap;
import com.pujjr.gps.dal.domain.HisFieldComment;
import com.pujjr.gps.service.ITableInitService;
import com.pujjr.gps.util.Utils;

@Transactional(rollbackFor=Exception.class)
@Service
public class TableInitServiceImpl implements ITableInitService {
	@Autowired
	private HisBeanMapMapper hisBeanMapMapper;
	@Autowired
	private HisFieldCommentMapper hisFieldCommentMapper;
	@Value("${jdbc_driverClassName_master}")
	private String jdbcDriverClass;
	@Value("${jdbc_url_master}")
	private String jdbcUrl;
	@Value("${jdbc_username_master}")
	private String userName;
	@Value("${jdbc_password_master}")
	private String passWord;
	@Override
	public void hisBeanMapInit() {
		// TODO Auto-generated method stub
//		ArrayList<HashMap<String,String>> al = new ArrayList<HashMap<String,String>>();
		String[][] table = new String[][] {
				{ "com.pujjr.gps.dal.domain.HisBeanMap", "对象映射表实体", "T_HIS_BEANMAP", "对象映射表" }, 
				{ "com.pujjr.gps.dal.domain.HisFieldComment", "数据表字段注释表实体", "T_HIS_FIELDCOMMENT", "数据表字段注释表" }, 
				{ "com.pujjr.gps.dal.domain.HisOper", "历史操作记录表实体", "T_HIS_OPER", "历史操作记录表" }, 
				{ "com.pujjr.gps.dal.domain.GpsStoreBranch", "经销商库存信息表实体", "T_APPLY", "经销商库存信息表" }
				
		};
		hisBeanMapMapper.deleteAll();
		for (int i = 0; i < table.length; i++) {
			String[] rowRecord = table[i];
			HisBeanMap hisBeanMap = new HisBeanMap();
			hisBeanMap.setId(Utils.get16UUID());
//			for (int j = 0; j < rowRecord.length; j++) {
//				System.out.println(rowRecord[j]);
//			}
			hisBeanMap.setClassName(rowRecord[0]);
			hisBeanMap.setClassCname(rowRecord[1]);
			hisBeanMap.setTableName(rowRecord[2]);
			hisBeanMap.setTableCname(rowRecord[3]);
			hisBeanMapMapper.insert(hisBeanMap);
		}
	}

	@Override
	public void hisFieldCommentInit() {
		String[] waitingInitTable = new String[]{
				"",
				"",
				""
		};
		List<HisFieldComment> list = null;
		try {
			String sql = "	select a.table_name,b.table_comment as table_cname,a.column_name as field_name,a.column_comment as field_cname from (select table_name,column_name,COLUMN_COMMENT from information_schema.columns where COLUMN_COMMENT <> '' )a "
					+ " left join (select table_name,table_comment from information_schema.TABLES where table_comment <> '')b "
					+ " on a.table_name = b.table_name ";
			Class.forName(jdbcDriverClass);
			Connection cnt = DriverManager.getConnection(jdbcUrl, userName, passWord);
			PreparedStatement ps = (PreparedStatement) cnt.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			list = new ArrayList<HisFieldComment>();
			while(rs.next()){
				ResultSetMetaData meta = (ResultSetMetaData) rs.getMetaData();
				Class cls = HisFieldComment.class;
				HisFieldComment obj = (HisFieldComment) cls.newInstance();
				for (int i = 0; i < meta.getColumnCount(); i++) {
					String columnType = meta.getColumnTypeName(i+1);//数据库对应type
					String columnLabel = meta.getColumnLabel(i+1);//sql语句对应列名
					String className = meta.getColumnClassName(i+1);
					String fieldName = Utils.col2Field(columnLabel);
					String setMethod = Utils.field2SetMethod(fieldName);
					Object value = rs.getObject(columnLabel);
					System.out.println(fieldName+"|"+columnType+"|"+className+"|"+columnLabel);
					Method settter = cls.getMethod(setMethod, className.getClass());
					settter.invoke(obj, value);
				}
				list.add(obj);
				System.out.println(obj.getTableName()+"|"+obj.getTableCname()+"|"+obj.getFieldName()+"|"+obj.getFieldCname());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<HisFieldComment> listExist = hisFieldCommentMapper.selectList();
		
		for (HisFieldComment hisFieldComment : listExist) {
			hisFieldCommentMapper.deleteByPrimaryKey(hisFieldComment.getId());
		}
		
		for (HisFieldComment hisFieldComment : list) {
			hisFieldComment.setId(Utils.get16UUID());
			hisFieldCommentMapper.insert(hisFieldComment);
		}
	}

}
