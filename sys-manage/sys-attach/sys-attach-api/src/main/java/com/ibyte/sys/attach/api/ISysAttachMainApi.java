package com.ibyte.sys.attach.api;

import com.ibyte.common.core.dto.IdVO;
import com.ibyte.common.core.dto.IdsDTO;
import com.ibyte.common.core.dto.MechanismDTO;
import com.ibyte.sys.attach.dto.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

/**
 * 附件操作API
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
public interface ISysAttachMainApi {

    /**
     * 绑定附件到主文档
     */
    @PostMapping("/bind")
    void bind(@RequestBody SysAttachBindVO bindVO);

    /**
     * 根据ID获取附件信息
     */
    @PostMapping("/detail")
    SysAttachVO detail(@RequestBody IdVO id);

    /**
     * 根据附件ID列表批量获取附件详情
     */
    @PostMapping("/listByIds")
    List<SysAttachVO> listByIds(@RequestBody IdsDTO ids);

    /**
     * 根据主文档查找附件列表
     */
    @PostMapping("/listByEntity")
    List<SysAttachVO> listByEntity(@RequestBody MechanismDTO entityVO);

    /**
     * 附件删除
     */
    @PostMapping("/delete")
    void delete(@RequestBody IdVO id);

    /**
     * 根据主文档删除附件
     */
    @PostMapping("/deleteByEntity")
    void deleteByEntity(@RequestBody MechanismDTO entityVO);

    /**
     * 附件克隆
     */
    @PostMapping("/clone")
    void clone(@RequestBody SysAttachCloneVO cloneVO);

    /**
     * 根据附件ID和读取范围获取文件字节
     */
    @PostMapping("/getFileData")
    SysAttachFileDataVO getFileData(@RequestBody SysAttachFileReadVO readVO);

    /**
     * 获取一次性下载链接
     */
    @PostMapping("/fetch-download-url")
    String fetchSingleUseDownloadUrl(@RequestBody IdVO idVO);
}