package org.billetsdoux.model;

/***
 * @Description:
 * @Param:
 * @return:
 * @Author: billets-doux
 * @Date: 11:42 下午 2020/4/2
 */
public class ByteHolder {
    private transient byte[] bytes;

    public byte[] bytes() {
        return bytes;
    }

    public void bytes(byte[] bytes) {
        this.bytes = bytes;

    }

    public int size() {
        return bytes == null ? 0 : bytes.length;
    }
}
