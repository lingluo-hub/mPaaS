package com.ibyte.attach.core.dto;

import com.ibyte.attach.core.entity.SysAttachCatalog;
import com.ibyte.attach.core.entity.SysAttachModuleLocation;
import lombok.*;

/**
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SysAttachUploadResultVO {

    private String fileId;

    private String filePath;

    private String fullPath;

    private SysAttachCatalog sysAttachCatalog;

    private SysAttachModuleLocation sysAttachModuleLocation;
}
