package com.sparta.paymentsystem.domain.cart.facade;

import com.sparta.paymentsystem.domain.cart.dto.AddCartRequest;
import com.sparta.paymentsystem.domain.cart.entity.CartItem;
import com.sparta.paymentsystem.domain.cart.service.CartService;
import com.sparta.paymentsystem.domain.member.entity.Member;
import com.sparta.paymentsystem.domain.member.service.MemberService;
import com.sparta.paymentsystem.domain.product.entity.Product;
import com.sparta.paymentsystem.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 장바구니 관련 복합 비즈니스 로직을 처리하는 파사드 클래스입니다.
 * Member, Product, Cart 서비스 간의 상호작용을 조율합니다.
 */
@Component
@RequiredArgsConstructor
/**
 * Facade 패턴을 사용하는 이유:
 * 1. 여러 도메인 서비스(Member, Product, Cart) 간의 의존성을 하나의 계층에서 관리하여 서비스 간의 결합도를 낮춤.
 * 2. 복잡한 비즈니스 로직의 흐름을 캡슐화하여 클라이언트(Controller)에게 단순한 인터페이스를 제공.
 */
public class CartFacade {

    private final CartService cartService;
    private final MemberService memberService;
    private final ProductService productService;

    /**
     * 장바구니에 상품을 추가합니다.
     *
     * @param memberId 사용자 ID
     * @param request  상품 ID와 수량이 포함된 요청 DTO
     * @return 생성된 장바구니 아이템의 ID
     */
    @Transactional
    public Long addItem(Long memberId, AddCartRequest request) {
        Member member = memberService.findById(memberId);
        Product product = productService.findProductEntity(request.productId());
        CartItem cartItem = new CartItem(member, product, request.quantity());
        return cartService.addItem(cartItem);
    }

}