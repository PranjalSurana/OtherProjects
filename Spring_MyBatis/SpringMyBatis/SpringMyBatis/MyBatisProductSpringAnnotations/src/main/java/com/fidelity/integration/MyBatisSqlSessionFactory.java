package com.fidelity.integration;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisSqlSessionFactory {
	private static SqlSessionFactory sqlSessionFactory;
	static {
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream("mybatis-config.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder()
					.build(inputStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static SqlSession openSession() {
		return sqlSessionFactory.openSession();
	}
}
