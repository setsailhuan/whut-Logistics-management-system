package com.qst.dms.entity;
import java.io.*;
public class AppendObjectOutputStream extends ObjectOutputStream{
	public static File file=null;
	public AppendObjectOutputStream(File file) throws IOException{
		super(new FileOutputStream(file,true));
	}
	public void writeStreamHeader() throws IOException{
		if(file!=null)
		{
			if(file.length()==0) {
				super.writeStreamHeader();
			}
			else {
				this.reset();//文件不为空的时候
			}
				}
		else {
			//文件不存在
			super.writeStreamHeader();
		}
	}
}
