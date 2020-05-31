package xdman.mediaconversion;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import xdman.ui.res.StringResource;

public class FormatLoader {
	public static List<FormatGroup> load() {
		List<FormatGroup> list = new ArrayList<>();
		try {
			InputStream inStream = StringResource.class.getResourceAsStream("/formats/format_db.txt");
			if (inStream == null) {
				inStream = new FileInputStream("formats/format_db.txt");
			}
			InputStreamReader r = new InputStreamReader(inStream, StandardCharsets.UTF_8);

			BufferedReader br = new BufferedReader(r);

			while (true) {
				String ln = br.readLine();
				if (ln == null||ln.length() < 1) {
					break;
				}
				FormatGroup fg = new FormatGroup();
				String[] arr = ln.split("\\|");
				fg.name = arr[0].trim();
				fg.desc = arr[1].trim();
				System.out.println("group: " + fg.name);
				list.add(fg);
			}
			while (true) {
				Format format = Format.read(br);
				if (format == null) {
					break;
				}
				print(format);
				for (FormatGroup fg : list) {
					if (fg.name.equals(format.group)) {
						System.out.println(fg.desc + " " + format.desc);
						fg.formats.add(format);
					}
				}
			}
		} catch (Exception ignored) {

		}
		return list;
	}

	static void print(Format format) {
		System.out.println("\t" + format.getDesc() + " '" + format.group + "'");
		List<String> list = format.getVideoCodecs();
		if (list.size() > 0) {
			System.out.print("\t\tVideo Codec:");
			for (String s : list) {
				if (s.length() > 1) {
					if (s.equals(format.getDefautVideoCodec())) {
						System.out.print("*");
					}

					System.out.print(s + " ");
				}
			}
			System.out.println("\n");
		}

		list = format.getResolutions();
		if (list.size() > 0) {
			System.out.print("\t\tResolution:");
			for (String s : list) {
				if (s.length() > 1) {
					if (s.equals(format.getDefaultResolution())) {
						System.out.print("*");
					}

					System.out.print(s + " ");
				}
			}
			System.out.println("\n");
		}

		list = format.getAudioChannel();
		if (list.size() > 0) {
			System.out.print("\t\tChannel:");
			for (String s : list) {
				if (s.equals(format.getDefaultAudioChannel())) {
					System.out.print("*");
				}

				System.out.print(s + " ");
			}
			System.out.println("\n");
		}

	}
}
