package com.imooc.ecommerce.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*主键ids*/
@ApiModel(description = "通用 id 对象")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableId {

    @ApiModelProperty(value = "数据表记录批量主键")
    private List<Id> ids;


    @ApiModel(description = "数据库记录主键对象")
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Id{

        @ApiModelProperty(value = "数据表记录主键")
        private Long id;

    }
}
