package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/validation/v2/items")
@RequiredArgsConstructor
public class ValidationItemControllerV2 {

    private final ItemRepository itemRepository;
    private final ItemValidator itemValidator;

    // 컨틀롤러에 검증기를 자동으로 적용할 수 있다. 컨트롤러에만 시용 가능
    @InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(itemValidator);
    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v2/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v2/addForm";
    }

    // @PostMapping("/add")
    // BindingResult를 반드시 Item 객체 다음에 넣어줘야함, model에 자동으로 포함된다
    public String addItemV1(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, 
        Model model) {

        /**
         * FieldError 생성자 요약
         * 필드에 오류가 있ㄹ으면 FieldError 객체를 생성해서 bindingResult에 담아두면 된다.
         * objectName : @ModelAttribute 이름
         * field : 오류가 발생한 필드 이름
         * defaultMessage: 오류 기본 메시지
         */
        if(!StringUtils.hasText(item.getItemName())) {
            bindingResult.addError(new FieldError("item", "itemName", "상품 이름은 필수 입니다."));
        }
        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.addError(new FieldError("item", "price", "가격은 1,000 ~ 1,000,000 까지 허용합니다."));
        }
        if(item.getQuantity() == null || item.getQuantity() >=9999) {
            bindingResult.addError(new FieldError("item", "quantity", "수량은 최대 9,999 까지 허용합니다."));
        }

        /**
         * ObjectError 생성자 요약
         * 특정 필드를 넘어서는 오류가 있으면 ObjectError객체를 생성해서 bindingResult에 담아두면 된다
         * objectName : @ModelAttribute의 이름
         * defaultMessage : 오류 기본 메시지
         */
        // 특정 필드가 아닌 복합 룰 검증
        if(item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice < 10000) {
                // 글로벌 오류는 ObjectError를 사용함
                bindingResult.addError(new ObjectError("item", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = "+ resultPrice));
            } 
        }

        // 검증 실패하면 다시 입력 폼으로
        if(bindingResult.hasErrors()) {
            log.info("errors = {} ", bindingResult);
            return "validation/v2/addForm";
        }
        
        // 성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    // @PostMapping("/add")
    public String addItemV2(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, 
        Model model) {

        /**
         * fieldError 생성자2
         * objectName : @ModelAttribute 이름
         * field : 오류가 발생한 필드 이름
         * rejectedValue: 사용자가 입력한 값(거절된 값)
         * bindingFailure : 타입오류 같은 바인딩 실패인지, 검증 실패인지 구분 값
         * codes : 메시지 코드
         * arguments : 메시지에서 사용하는 인자
         * defaultMessage: 오류 기본 메시지
         */
        if(!StringUtils.hasText(item.getItemName())) {
            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, null, null, "상품 이름은 필수 입니다."));
        }
        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.addError(new FieldError("item", "price", item.getPrice(), false, null, null, "가격은 1,000 ~ 1,000,000 까지 허용합니다."));
        }
        if(item.getQuantity() == null || item.getQuantity() >=9999) {
            bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false, null, null, "수량은 최대 9,999 까지 허용합니다."));
        }

        // 특정 필드가 아닌 복합 룰 검증
        if(item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice < 10000) {
                // 글로벌 오류
                bindingResult.addError(new ObjectError("item", null, null, "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = "+ resultPrice));
            } 
        }
        // 검증 실패하면 다시 입력 폼으로
        if(bindingResult.hasErrors()) {
            log.info("errors = {} ", bindingResult);
            return "validation/v2/addForm";
        }
        
        // 성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    // @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, 
        Model model) {

        /**
         * new String[]{"1", "2"}
         * 첫번째 값이 없으면 자동으로 2번째 값을 찾고 2번째 값도 없다면 defaultMessage를 출력한다
         */
        if(!StringUtils.hasText(item.getItemName())) {
            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, new String[]{"required.item.itemName"}, null, null));
        }
        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.addError(new FieldError("item", "price", item.getPrice(), false, new String[]{"range.item.price"}, new Object[]{1000, 1000000}, null));
        }
        if(item.getQuantity() == null || item.getQuantity() >=9999) {
            bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false, new String[]{"max.item.quantity"}, new Object[]{9999}, null));
        }

        // 특정 필드가 아닌 복합 룰 검증
        if(item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice < 10000) {
                // 글로벌 오류
                bindingResult.addError(new ObjectError("item", new String[]{"totalPriceMin"}, new Object[]{10000, resultPrice}, null));
            } 
        }
        // 검증 실패하면 다시 입력 폼으로
        if(bindingResult.hasErrors()) {
            log.info("errors = {} ", bindingResult);
            return "validation/v2/addForm";
        }
        
        // 성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    // @PostMapping("/add")
    public String addItemV4(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, 
        Model model) {

        log.info("objectName={}", bindingResult.getObjectName());
        log.info("target={}", bindingResult.getTarget());

        /**
         * rejectValue()
         * field : 오류 필드명
         * errorCode : 오류 코드
         * errorArgs: 오류 메시지에서 {0}을 치환하기 위한 값
         * defaultMessage : 오류 메시지를 찾을 수 없을때 사용하는 기본 메시지
         */
        if(!StringUtils.hasText(item.getItemName())) {
            bindingResult.rejectValue("itemName", "required");
        }
        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.rejectValue("price", "range", new Object[]{1000, 10000000}, null);
        }
        if(item.getQuantity() == null || item.getQuantity() >=9999) {
            bindingResult.rejectValue("quantity", "max", new Object[]{9999}, null);
        }

        // 특정 필드가 아닌 복합 룰 검증
        if(item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice < 10000) {
                bindingResult.rejectValue("item", "totalPriceMin", new Object[]{10000, resultPrice}, null);
            } 
        }
        
        // 검증 실패하면 다시 입력 폼으로
        if(bindingResult.hasErrors()) {
            log.info("errors = {} ", bindingResult);
            return "validation/v2/addForm";
        }
        
        // 성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    // @PostMapping("/add")
    public String addItemV5(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, 
        Model model) {

        itemValidator.validate(item, bindingResult);
   
        // 검증 실패하면 다시 입력 폼으로
        if(bindingResult.hasErrors()) {
            log.info("errors = {} ", bindingResult);
            return "validation/v2/addForm";
        }
        
        // 성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    /**
     * @Validated는 검증기를 실행하라는 애노테이션이다.
     * 이 애노테이션이 붙으면 앞서 WebDataBinder에 등록한 검증기를 찾아서 실행한다.
     * 이 과정에서 supports()가 호출되고 결과가 true이므로 ItemValidator의 validate()가 호출된다.
     */ 
    @PostMapping("/add")
    public String addItemV6(@Validated @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, 
        Model model) {

        // 검증 실패하면 다시 입력 폼으로
        if(bindingResult.hasErrors()) {
            log.info("errors = {} ", bindingResult);
            return "validation/v2/addForm";
        }
        
        // 성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v2/items/{itemId}";
    }

}

