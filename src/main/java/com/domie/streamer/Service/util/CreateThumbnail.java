package com.domie.streamer.Service.util;

import java.io.IOException;

public class CreateThumbnail  {

	public static void createThumbNail(String srcFile, String destFile) throws InterruptedException, IOException {
		Runtime run = Runtime.getRuntime();

		Process p = null;
		String cmd = "ffmpeg -i "+srcFile+" -ss 00:00:14.435 -vframes 1 "+destFile;
		p = run.exec(cmd);

		p.getErrorStream();
		p.waitFor();

		p.destroy();
	}
}
