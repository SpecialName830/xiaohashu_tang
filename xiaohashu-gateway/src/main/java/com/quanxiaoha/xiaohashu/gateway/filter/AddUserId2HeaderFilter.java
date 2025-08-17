package com.quanxiaoha.xiaohashu.gateway.filter;

import cn.dev33.satoken.stp.StpUtil;
import com.quanxiaoha.framework.common.constants.GlobalConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AddUserId2HeaderFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("==================> TokenConvertFilter");

        Long userId = null;

        try {
            userId = StpUtil.getLoginIdAsLong();
        }catch (Exception e){
            // 若未登录
            log.info("未成功过获取userId");
            chain.filter(exchange);
        }

        log.info("===> 当前登录的用户id: {}", userId);

        Long finalUserId = userId;
        ServerWebExchange webExchange = exchange.mutate().request(builder -> builder.header(GlobalConstants.USER_ID, String.valueOf(finalUserId)))
                .build();
        return chain.filter(webExchange);
    }
}
