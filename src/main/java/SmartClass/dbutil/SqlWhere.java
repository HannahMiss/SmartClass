package SmartClass.dbutil;

import java.util.ArrayList;
import java.util.List;

public class SqlWhere
{
	ArrayList<String> conditions = new ArrayList<String>();
	
	public void add(String condition)
	{
		conditions.add(condition);
	}

	/**/
	// 字符串值 比较
	public void addExact(String colName, String colValue)
	{
		conditions.add( colName + "='" + colValue + "'");
	}
	//整数值	比较
	public void addExact(String colName, int colValue)
	{
		conditions.add( colName + "=" + colValue + "");
	}
	//长整形比较
	public void addExact(String colName, Long colValue)
	{
		conditions.add( colName + "=" + colValue + "");
	}


	//模糊匹配
	public void addLike(String colName, String colValue)
	{
		conditions.add( colName + " LIKE '%" + colValue + "%'");
	}
	//  WHERE id IN ('07922860270B47FDB02ADD231F885DAB', '08C74DB5EBC64F439BB747EECBEC3862')
	public void addIn(String colName, List values)
	{
		if(values.size() == 0) return;
		
		String sql = " " + colName + " IN (";
		for(int i=0; i<values.size(); i++)
		{
			String s = "'" + values.get(i) + "'";
			sql += s;
			if(i != values.size() - 1) sql += ",";
		}
		
		sql += ") ";
		conditions.add(sql);
	}
	public void addIn(String colName, Object[] values)
	{
		if(values.length == 0) return;
		
		String sql = " " + colName + " IN (";
		for(int i=0; i<values.length; i++)
		{
			String a = values[i].toString().trim();
			if(a.length() == 0) continue;
			
			String s = "'" + a + "'";
			sql += s;
			if(i != values.length - 1) sql += ",";
		}
		
		sql += ") ";
		conditions.add(sql);
	}

	/*添加大于或等于查询条件*/
	// 字符串
	public void addGtE(String colName, String colValue)
	{
		conditions.add( colName + ">='" + colValue + "'");
	}
	//整数值	比较
	public void addGtE(String colName, int colValue)
	{
		conditions.add( colName + ">=" + colValue + "");
	}

	/*添加小于或等于查询条件*/
	// 字符串
	public void addLtE(String colName, String colValue)
	{
		conditions.add( colName + "<='" + colValue + "'");
	}
	//整数值	比较
	public void addLtE(String colName, int colValue)
	{
		conditions.add(colName + "<=" + colValue + "");
	}
	@Override
	public String toString()
	{
		if(conditions.size() == 0) return " ";
		
		String where = " where ";
		for(int i=0; i<conditions.size() ;i++)
		{
			if(i != 0) where += " and ";
			where += " (" + conditions.get(i) + ") ";
		}
		return where;
	}
	
	
}
