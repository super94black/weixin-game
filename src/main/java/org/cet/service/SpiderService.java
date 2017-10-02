package org.cet.service;

import org.cet.util.Result;
import java.io.IOException;

/**
 * @Author zhang
 * @Date 2017/9/27 20:08
 * @Content
 */
public interface SpiderService {
    public Result getClassTableFromJwzx(String stuNum) throws IOException;
}
