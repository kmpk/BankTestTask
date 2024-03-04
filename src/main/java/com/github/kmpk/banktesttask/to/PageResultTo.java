package com.github.kmpk.banktesttask.to;

import java.util.List;

public record PageResultTo(List<Object> content, int pageNum, int totalPages, long totalItems) {
}
