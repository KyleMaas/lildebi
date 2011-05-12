package info.guardianproject.lildebi;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: kevin
 * Date: 4/1/11
 * Time: 3:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class StreamThread extends Thread
{
    InputStream i;
    StreamUpdate update;

    StreamThread(InputStream i)
    {
        this.i = i;
        this.update = update;
    }

    StreamThread(InputStream i, StreamUpdate update)
    {
        this.i = i;
        this.update = update;
    }

    @Override
    public void run()
    {
        int next;
        try
        {
            byte[] readBuffer = new byte[512];
            int readCount = -1;
            while ((readCount = i.read(readBuffer)) > 0)
            {
                String readString = new String(readBuffer, 0, readCount);
                update.update(readString);
            }
        }
        catch (IOException e)
        {
            App.loge("", e);
        }
    }

    public String dump()
    {
        if(update instanceof StringBufferStreamUpdate)
        {
            return ((StringBufferStreamUpdate)update).dump();
        }

        return null;
    }

    public static abstract class StreamUpdate
    {
        public abstract void update(String val);
    }

    public class StringBufferStreamUpdate extends StreamUpdate
    {
        StringBuilder sb = new StringBuilder();

        @Override
        public void update(String val)
        {
            sb.append(val);
        }

        public String dump()
        {
            return sb.toString();
        }
    }
}