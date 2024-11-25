insert into transformer (group_id, transformer_id, transformer_code, transformer_name, is_active)
values (1, 1, 'FIND_AND_DELETE', 'Find matches in element value and remove it from original value', true),
       (1, 2, 'FIND_AND_REPLACE', 'Find matches in original values and replace them', true),
       (1, 3, 'CYRILLIC', 'Detect Cyrillic and Greek letters and convert to Latin', true);
