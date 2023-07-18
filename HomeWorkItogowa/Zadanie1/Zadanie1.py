import json
from datetime import datetime

def save_notes(notes, filename):
    with open(filename, 'w') as file:
        json.dump(notes, file, indent=4)

def load_notes(filename):
    try:
        with open(filename, 'r') as file:
            return json.load(file)
    except FileNotFoundError:
        return []

def create_note():
    note_id = input("Введите идентификатор заметки: ")
    title = input("Введите заголовок заметки: ")
    body = input("Введите текст заметки: ")
    creation_date = datetime.now().isoformat()
    last_modified = datetime.now().isoformat()

    return {
        "id": note_id,
        "title": title,
        "body": body,
        "creation_date": creation_date,
        "last_modified": last_modified
    }

def display_notes(notes):
    if not notes:
        print("Список заметок пуст")
    else:
        for note in notes:
            print(f"ID: {note['id']}")
            print(f"Заголовок: {note['title']}")
            print(f"Текст: {note['body']}")
            print(f"Дата создания: {note['creation_date']}")
            print(f"Дата последнего изменения: {note['last_modified']}")
            print()

def find_note_by_id(notes, note_id):
    for note in notes:
        if note['id'] == note_id:
            return note
    return None

def edit_note(note):
    new_title = input("Введите новый заголовок заметки: ")
    new_body = input("Введите новый текст заметки: ")
    note['title'] = new_title
    note['body'] = new_body
    note['last_modified'] = datetime.now().isoformat()

def delete_note(notes, note_id):
    note = find_note_by_id(notes, note_id)
    if note:
        notes.remove(note)
        print("Заметка успешно удалена")
    else:
        print("Заметка с указанным ID не найдена")

# Загрузка заметок из файла
notes = load_notes("notes.json")

while True:
    print("1. Просмотреть все заметки")
    print("2. Добавить новую заметку")
    print("3. Редактировать заметку")
    print("4. Удалить заметку")
    print("5. Выйти")

    choice = input("Выберите действие (введите номер): ")

    if choice == '1':
        display_notes(notes)
    elif choice == '2':
        new_note = create_note()
        notes.append(new_note)
        save_notes(notes, "notes.json")
        print("Заметка успешно добавлена")
    elif choice == '3':
        note_id = input("Введите ID заметки для редактирования: ")
        note = find_note_by_id(notes, note_id)
        if note:
            edit_note(note)
            save_notes(notes, "notes.json")
            print("Заметка успешно отредактирована")
        else:
            print("Заметка с указанным ID не найдена")
    elif choice == '4':
        note_id = input("Введите ID заметки для удаления: ")
        delete_note(notes, note_id)
        save_notes(notes, "notes.json")
    elif choice == '5':
        print("Выход из программы")
        break
    else:
        print("Некорректный выбор, попробуйте снова")
