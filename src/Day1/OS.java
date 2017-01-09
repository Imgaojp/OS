package Day1;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 　　　　　　　　┏┓　　　┏┓+ +
 * 　　　　　　　┏┛┻━━━┛┻┓ + +
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃ ++ + + +
 * 　　　　　　 ████━████ ┃+
 * 　　　　　　　┃　　　　　　　┃ +
 * 　　　　　　　┃　　　┻　　　┃
 * 　　　　　　　┃　　　　　　　┃ + +
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃ + + + +
 * 　　　　　　　　　┃　　　┃　　　　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃ + 　　　　神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃　　+
 * 　　　　　　　　　┃　 　　┗━━━┓ + +
 * 　　　　　　　　　┃ 　　　　　　　┣┓
 * 　　　　　　　　　┃ 　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛ + + + +
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛+ + + +
 * Created by Imgaojp on 2017/1/9.
 */
public class OS {

    /**
     * 填充img文件前0x90字节
     */
    private int[] imgContent = new int[]{
            0XEB, 0X4E, 0X90, 0X48, 0X45, 0X4C, 0X4C, 0X4F, 0X49, 0X50, 0X4C, 0X00, 0X02, 0X01, 0X01, 0X00,     //0X00-0X0F
            0X02, 0XE0, 0X00, 0X40, 0X0B, 0XF0, 0X09, 0X00, 0X12, 0X00, 0X02, 0X00, 0X00, 0X00, 0X00, 0X00,     //0X10-0X1F
            0X40, 0X0B, 0X00, 0X00, 0X00, 0X00, 0X29, 0XFF, 0XFF, 0XFF, 0XFF, 0X48, 0X45, 0X4C, 0X4C, 0X4F,     //0X20-0X2F
            0X2D, 0X4F, 0X53, 0X20, 0X20, 0X20, 0X46, 0X41, 0X54, 0X31, 0X32, 0X20, 0X20, 0X20, 0X00, 0X00,     //0X30-0X3F
            0X00, 0X00, 0X00, 0X00, 0X00, 0X00, 0X00, 0X00, 0X00, 0X00, 0X00, 0X00, 0X00, 0X00, 0X00, 0X00,     //0X40-0X4F
            0XB8, 0X00, 0X00, 0X8E, 0XD0, 0XBC, 0X00, 0X7C, 0X8E, 0XD8, 0X8E, 0XC0, 0XBE, 0X74, 0X7C, 0X8A,     //0X50-0X5F
            0X04, 0X83, 0XC6, 0X01, 0X3C, 0X00, 0X74, 0X09, 0XB4, 0X0E, 0XBB, 0X0F, 0X00, 0XCD, 0X10, 0XEB,     //0X60-0X6F
            0XEE, 0XF4, 0XEB, 0XFD//, 0X0A, 0X0A, 0X68, 0X65, 0X6C, 0X6C, 0X6F, 0X2C, 0X20, 0X77, 0X6F, 0X72,     //0X70-0X7F
            //0X6C, 0X64, 0X0A, 0X00, 0X00, 0X00, 0X00, 0X00, 0X00, 0X00, 0X00, 0X00, 0X00, 0X00, 0X00, 0X00      //0X80-0X8F
    };

    /**
     * 链表储存Byte值
     */
    private ArrayList<Integer> imgToByte = new ArrayList<>();

    /**
     * 构造函数
     *
     * @param s
     */
    public OS(String s) {
        for (int i = 0; i < imgContent.length; i++) {       //填充数组部分
            imgToByte.add(imgContent[i]);
        }

        imgToByte.add(0X0A);
        imgToByte.add(0X0A);                                //添加0X0A
        for (int i = 0; i < s.length(); i++) {
            imgToByte.add((int) s.charAt(i));
        }
        imgToByte.add(0X0A);                                //添加0X0A

        int length = 0X1FE;                                 //填充0X1FE
        for (int i = imgToByte.size(); i < length; i++) {
            imgToByte.add(0X00);
        }
        imgToByte.add(0X55);
        imgToByte.add(0XAA);
        imgToByte.add(0XF0);
        imgToByte.add(0XFF);

        for (int i = imgToByte.size(); i < 0X1400; i++) {    //填充0X1400
            imgToByte.add(0X00);
        }

        imgToByte.add(0XF0);
        imgToByte.add(0XFF);
        imgToByte.add(0XFF);

        for (int i = imgToByte.size(); i < 0X168000; i++) {     //填充剩下字节
            imgToByte.add(0X00);
        }
    }

    /**
     * 从Integer链表制作软盘文件
     */
    public void makeFloppy() {
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream("System.img"));
            for (int i = 0; i < imgToByte.size(); i++) {
                out.writeByte(imgToByte.get(i).byteValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        OS os = new OS("Hello World!Gabon Gao!");
        os.makeFloppy();
    }

}
