package com.ethan.finance.web;

import java.util.List;

record ApiErrorResponse(List<String> errors) {
}
