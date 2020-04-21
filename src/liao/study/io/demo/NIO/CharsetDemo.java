package liao.study.io.demo.NIO;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/*
 * @description: 字符集编码
 *      Charset
 * @author: Liao
 * @date  2020/4/18 13:09
 */
public class CharsetDemo {

    public static void main(String[] args) throws CharacterCodingException {

/*
        //得到所有 Charset 所支持的编码
        SortedMap<String, Charset> charsetMap = Charset.availableCharsets();

        charsetMap.forEach((k,v) -> {
            System.out.println(k + " --- " + v);
        });
*/

        //设置当前编码格式
        Charset charset = Charset.forName("GBK");

        //获取编码器
        CharsetEncoder charsetEncoder = charset.newEncoder();

        //获取解码器
        CharsetDecoder charsetDecoder = charset.newDecoder();

        //设置Buffer
        CharBuffer buffer = CharBuffer.allocate(1024);
        buffer.put("你好");
        buffer.flip();

        //编码
        ByteBuffer encode = charsetEncoder.encode(buffer);

        //解码
        CharBuffer decode = charsetDecoder.decode(encode);

        /**
         * 注意事项：读模式 需要在读取之前设置，不能在解码之前设置为读模式。
         *
         * 切换成读模式
         */
        encode.flip();
        System.out.println(decode.toString());
    }

}
