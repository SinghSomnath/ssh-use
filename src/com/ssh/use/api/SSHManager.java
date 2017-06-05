package com.ssh.use.api;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SSHManager {

	public JSch jsch= null;
	public String response = "";
	//	public static Session session= null;
	public Session session= null;
	public Properties config = null;



	public SSHManager(String sIP,String uName,String passWord) 
	{
		jsch = new JSch();
		try {
			session=jsch.getSession(uName, sIP);
			session.setPassword(passWord);

		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
	}

	public String  executeCommand(String command) 
	{
		String response="";

		System.out.println(command);
		
		try {

			session.connect();
			ChannelExec channel=(ChannelExec) session.openChannel("exec");
			BufferedReader in=new BufferedReader(new InputStreamReader(channel.getInputStream()));
			channel.setCommand(command);		
			channel.connect();


			String msg = "";
			//			TimeUnit.MINUTES.sleep(1);
			//			Date date1 = new Date();
			//			System.out.println("..som1...");
			while((msg=in.readLine())!=null){	
				//				Date date2 = new Date();
				//				System.out.println("som2..." + (date2.getTime() - date1.getTime()));
				response = msg + "\n" + response;

			}

			System.out.println("Response of >>>> " +command +" >>>> " + response);

			channel.disconnect();
			//			System.out.println("...som3..");
			session.disconnect();
			//			System.out.println("...som4..");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {

			return response;
		}
	}




	public void copyFile(String sToDir,String sFileName)
	{
		ChannelSftp channelSftp = null;

		try {

			session.connect();
			channelSftp = (ChannelSftp)session.openChannel("sftp");			
			channelSftp.connect();

			channelSftp.cd(sToDir);

			channelSftp.put(new FileInputStream("data/job/" + sFileName), sFileName, ChannelSftp.OVERWRITE);


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {

			channelSftp.disconnect();
			session.disconnect();
		}

	}


	public String  executeCommandWithOutPrintingResponse(String command) 
	{
		String response="";

		try {

			session.connect();
			ChannelExec channel=(ChannelExec) session.openChannel("exec");
			BufferedReader in=new BufferedReader(new InputStreamReader(channel.getInputStream()));
			channel.setCommand(command);		
			channel.connect();


			String msg = "";
			while((msg=in.readLine())!=null){				
				response = msg + "\n" + response;

			}

			//			System.out.println("Response of >>>> " +command +" >>>> " + response);
			channel.disconnect();
			session.disconnect();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {

			return response;
		}
	}

	public static String  executeCommand(String sIP,String uName,String passWord,String command) 
	{
		String quick_response="";
		JSch quick_jsch= null;
		Session quick_session= null;
		Properties quick_config = null;


		//Creating JSCH session

		quick_jsch = new JSch();
		try {
			quick_session=quick_jsch.getSession(uName, sIP);
			quick_session.setPassword(passWord);

		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		quick_config = new Properties();
		quick_config.put("StrictHostKeyChecking", "no");
		quick_session.setConfig(quick_config);



		//Executing the command .
		
		


		try {
			quick_session.connect();
			ChannelExec quick_channel=(ChannelExec) quick_session.openChannel("exec");

			BufferedReader in=new BufferedReader(new InputStreamReader(quick_channel.getInputStream()));
			quick_channel.setCommand(command);		
			quick_channel.connect();

			try {
				Thread.sleep(2000);

			} catch (Exception e) {}
			String msg = "";
			while((msg=in.readLine())!=null){				
				quick_response = msg + "\n" + quick_response;

			}

			System.out.println("Response of  >>>> " +command +" >>>> " + quick_response);
			quick_channel.disconnect();
			quick_session.disconnect();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {

			return quick_response;
		}
	}


	
}
