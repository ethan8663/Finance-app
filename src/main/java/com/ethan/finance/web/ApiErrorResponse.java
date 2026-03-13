package com.ethan.finance.web;

import java.util.List;

public record ApiErrorResponse(List<String> errors) {
}
