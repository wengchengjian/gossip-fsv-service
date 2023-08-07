/**
 * @author wengchengjian
 * @date 2023/8/3-16:53
 */
module gossip.fsv.service.gossip.fsv.model.main {
    exports com.weng.fsv.model.enums;
    exports com.weng.fsv.model.base;
    exports com.weng.fsv.model.fsv;
    exports com.weng.fsv.model.manga;
    exports com.weng.fsv.model.novel;
    requires lombok;
    requires jakarta.persistence;
    requires com.fasterxml.jackson.databind;
    requires com.baomidou.mybatis.plus.annotation;
    requires jakarta.validation;
    requires spring.context;
    requires spring.boot.autoconfigure;
    requires org.mybatis.spring;
}