package com.mengyou.library.util;

import java.util.HashMap;
import java.util.Map;

/**
 * ״̬��Ӧ��
 * @author Administrator
 *
 */
public class NetCodeContacts {

	//code
	public static final int DB_ERROR=1;
	public static final int SUCCESS=0;
	public static final int FORMAT_ERROR=2;
	public static final int OTHER_ERROR=2;
	//message
	public static final String DB_ERROR_MESSAGE="数据库操作错误";
	public static final String SUCCESS_MESSAGE="响应成功";
	public static final String FORMAT_ERROR_MESSAGE="数据格式错误";
	public static final String OTHER_ERROR_MESSAGE="其他错误";
	
	public static final String DATE_NULL="数据为空";
	public static final String STUDENT_LOGIN_SUCCESS="学生登录成功";
	public static final String TEACHER_LOGIN_SUCCESS="老师登录成功";
	public static final String STUDENT_LOGIN_ERROR="学生登录失败";
	public static final String TEACHER_LOGIN_ERROR="老师登录失败";
	public static final String LOGIN_ERROR="登录失败";
	//map
	
	public static Map<Integer,String> netCodeMap=new HashMap<Integer, String>();
	
	static
	{
		netCodeMap.put(DB_ERROR, DB_ERROR_MESSAGE);
		netCodeMap.put(SUCCESS, SUCCESS_MESSAGE);
		netCodeMap.put(FORMAT_ERROR, FORMAT_ERROR_MESSAGE);
		netCodeMap.put(OTHER_ERROR, OTHER_ERROR_MESSAGE);
	}
}
