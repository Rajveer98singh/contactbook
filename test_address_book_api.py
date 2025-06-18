import requests
import json

BASE_URL = "http://localhost:8080"

def test_create_contacts():
    url = f"{BASE_URL}/create"
    payload = [
        {"name": "Alice Smith", "phone": "1234567890", "email": "alice@example.com"},
        {"name": "Bob Jones", "phone": "2345678901", "email": "bob@example.com"}
    ]
    response = requests.post(url, json=payload)
    assert response.status_code == 200
    data = response.json()
    assert len(data) == 2
    for contact in data:
        assert "id" in contact
        assert contact["name"] in ["Alice Smith", "Bob Jones"]
    return data  # return to use their IDs later

def test_update_contacts(created):
    url = f"{BASE_URL}/update"
    payload = [
        {"id": created[0]["id"], "phone": "9999999999"},
        {"id": created[1]["id"], "email": "newbob@example.com"}
    ]
    response = requests.put(url, json=payload)
    assert response.status_code == 200
    data = response.json()
    assert data[0]["phone"] == "9999999999"
    assert data[1]["email"] == "newbob@example.com"

def test_search_contacts():
    url = f"{BASE_URL}/search"
    payload = {"query": "Smith"}
    response = requests.post(url, json=payload)
    assert response.status_code == 200
    results = response.json()
    assert any("Smith" in c["name"] for c in results)

def test_delete_contacts(created):
    url = f"{BASE_URL}/delete"
    ids = [c["id"] for c in created]
    response = requests.delete(url, json=ids)
    assert response.status_code == 200
    result = response.json()
    assert result.get("deleted") == 2

def run_tests():
    print("Testing /create...")
    created = test_create_contacts()
    print("Testing /update...")
    test_update_contacts(created)
    print("Testing /search...")
    test_search_contacts()
    print("Testing /delete...")
    test_delete_contacts(created)
    print("✅ All tests passed successfully.")

if __name__ == "__main__":
    run_tests()