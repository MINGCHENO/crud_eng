package com.ykm.util;

import org.activiti.engine.ProcessEngineConfiguration;

public class CreateActivitiTableUtil {

	/**
	 * ����Activiti��Ҫ��25��
	 */
	//@Test
	public void testCreateTable(){
		ProcessEngineConfiguration pec=ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration(); // ��ȡ������������
		pec.setJdbcDriver("com.mysql.cj.jdbc.Driver"); // ��������
		pec.setJdbcUrl("jdbc:mysql://localhost:3306/db_studentLeave2"); // �������ӵ�ַ
		pec.setJdbcUsername("root"); // �û���
		pec.setJdbcPassword("123456"); // ����
		
		/**
		 * ����ģʽ  true �Զ������͸��±�
		 */
		pec.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		
		// ��ȡ�����������
		pec.buildProcessEngine(); 
	}

}
