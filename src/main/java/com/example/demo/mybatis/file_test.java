package com.example.demo.mybatis;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class file_test {

	{
		System.out.println("123");
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File a=new File("C:\\test.txt");
		FileInputStream is=new FileInputStream(a);
		int len=(int) a.length();
		byte[] b=new byte[len];
		int q=is.read(b);
		String s=new String(b);
		System.out.println(s.length()+q);
	}

}
