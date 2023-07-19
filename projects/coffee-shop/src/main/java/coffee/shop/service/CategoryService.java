package coffee.shop.service;

import coffee.shop.constant.MessageConstants;
import coffee.shop.converter.CategoryConverter;
import coffee.shop.dto.CategoryDTO;
import coffee.shop.entity.Category;
import coffee.shop.model.exception.NotFoundException;
import coffee.shop.model.request.CategoryRequest;
import coffee.shop.model.response.CommonDataPageResponse;
import coffee.shop.model.response.CommonDataResponse;
import coffee.shop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CommonDataResponse addCategory(CategoryRequest categoryRequest) {
        final var name = categoryRequest.getName();
        validateName(name);
        final var category = Category.builder()
                .name(name)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        final var data = categoryRepository.save(category);
        return CommonDataResponse.builder()
                .result(true)
                .data(CategoryConverter.convert(data))
                .build();
    }

    public CommonDataPageResponse getCategories(Integer pageNumber, Integer pageSize) {
        final Pageable pageable = PageRequest.of(pageNumber, pageSize);
        final Page<Category> categories = categoryRepository.findAll(pageable);
        final List<CategoryDTO> categoriesResponse = categories.get()
                .map(CategoryConverter::convert)
                .collect(Collectors.toList());
        return CommonDataPageResponse.builder()
                .data(categoriesResponse)
                .pageSize(categories.getSize())
                .pageNumber(categories.getNumber())
                .totalPages(categories.getTotalPages())
                .totalElements(categories.getTotalElements())
                .build();
    }

    public CommonDataResponse getCategoryDetail(Long categoryId) {
        final Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(MessageConstants.CATEGORY_NOT_FOUND));
        return CommonDataResponse.builder()
                .result(true)
                .data(CategoryConverter.convert(category))
                .build();
    }

    public CommonDataResponse updateCategory(Long categoryId, CategoryRequest categoryRequest) {
        final var name = categoryRequest.getName();
        validateName(name);
        final Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(MessageConstants.CATEGORY_NOT_FOUND));
        category.setName(name);
        category.setUpdatedAt(LocalDateTime.now());
        final var data = categoryRepository.save(category);
        return CommonDataResponse.builder()
                .result(true)
                .data(CategoryConverter.convert(data))
                .build();
    }

    public CommonDataResponse deleteCategory(Long categoryId) {
        final Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(MessageConstants.CATEGORY_NOT_FOUND));
        categoryRepository.delete(category);
        return CommonDataResponse.builder()
                .result(true)
                .data(MessageConstants.CATEGORY_DELETE_SUCCESS)
                .build();
    }

    private static void validateName(String name) {
        if (name == null || name.isEmpty()) {
            log.warn(MessageConstants.CATEGORY_NAME_INVALID);
            throw new IllegalArgumentException(MessageConstants.CATEGORY_NAME_INVALID);
        }
    }
}
