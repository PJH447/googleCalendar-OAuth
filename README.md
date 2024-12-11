### 키 생성
![스크린샷 2024-12-11 오후 3 38 47](https://github.com/user-attachments/assets/186fa8ba-4fda-4e53-bc03-71b65698d99a)

### 키 등록
<img width="425" alt="스크린샷 2024-12-11 오후 3 40 10" src="https://github.com/user-attachments/assets/a23d892f-c877-4cad-b805-e6d136929baf">

```
application.yml
google:
  calendar:
    iss: '{json-client_email}'
    private-key-id: '{json-private_key_id}'
    private-key: {'-----BEGIN PRIVATE KEY-----\(..생략..)-----END PRIVATE KEY-----\n}'
    expiration-time: 3600000
    calendar-id: '{calendar-id@group.calendar.google.com}'
    holiday-calendar-id: 'ko.south_korea#holiday@group.v.calendar.google.com'
```



![스크린샷 2024-12-11 오후 3 32 17](https://github.com/user-attachments/assets/c57aaea7-34b0-4d10-83b1-bedcffa15a3e)







