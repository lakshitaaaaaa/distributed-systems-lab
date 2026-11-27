
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class prac5 {
    public static void main(String[] args) {
        try {
            RandomAccessFile file =
                new RandomAccessFile("shared.dat", "rw");

            FileChannel channel = file.getChannel();

            MappedByteBuffer buffer =
                channel.map(FileChannel.MapMode.READ_WRITE,
                            0, 4);

            buffer.putInt(0, 0);

            for (int i = 0; i < 10; i++) {
                int count = buffer.getInt(0);
                count++;
                buffer.putInt(0, count);

                System.out.println(
                    "Counter: " + count);
            }

            System.out.println(
                "Final Counter Value: " +
                buffer.getInt(0));

            channel.close();
            file.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
