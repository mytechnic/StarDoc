package com.github.mytechnic.doc.domain.reader;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class ApiDocumentMeta {

    private final List<ClassMeta> classMetas = new ArrayList<>();
}
