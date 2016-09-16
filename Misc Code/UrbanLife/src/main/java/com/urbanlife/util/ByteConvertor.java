package com.urbanlife.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import javax.mail.MessagingException;
import javax.mail.internet.MimeUtility;

public class ByteConvertor {

	public ByteConvertor() {
	}

	public static byte[] convertObjectToBytes(Object obj) throws IOException {
		byte[] convertedBytes = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		OutputStream mout = null;
		try {
			try {
				mout = MimeUtility.encode(bos, "base64");
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out = new ObjectOutputStream(mout);
			out.writeObject(obj);
			convertedBytes = bos.toByteArray();

		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException ex) {
				// ignore close exception
			}
			try {
				bos.close();
			} catch (IOException ex) {
				// ignore close exception
			}
		}
		return convertedBytes;
	}

	public static Object convertBytesToObjcet(byte[] yourBytes)
			throws IOException {

		Object o = null;
		ByteArrayInputStream bis = new ByteArrayInputStream(yourBytes);
		ObjectInput in = null;
		try {
			in = new ObjectInputStream(MimeUtility.decode(bis, "base64"));
			o = in.readObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}
}
