# FinalProject: Система LikeIT 
![logo](https://cloud.githubusercontent.com/assets/13800212/18850899/33ea0a60-8442-11e6-9f50-145b34222021.png)
<p>Эта система представляет собой форум, на котором люди, любящие и занимающиеся программированием, могут задать интересующие их вопросы и получить на них ответы от других пользователей системы, а также сами могут помочь другим, ответив на их вопросы.</p>
<p>Есть система подсчета рейтинга для вопросов\комментариев\пользователей. С целью предотвращения пересчета рейтинга при удалении данных о вопросах\комментариях\ пользователях было принято решение заменить полное удаление данных на систему архивирования: ставится флаг, помечающий, что данные находятся в архиве. Обычные пользователи не имеют доступа к архиву, да и не подозревают о его существовании. (P.S. здесь и далее под «удалить» подразумевается помещение в архив).</p>
<p>Любой <b>Пользователь</b>, в том числе незарегистрированный, может просмотреть каталог вопросов, самые популярные вопросы, последние вопросы, список зарегистрированных пользователей, их профайлы и информацию о форуме.</p>
<p>Авторизованный <b>Пользователь</b> может создать сообщение с просьбой о помощи в некотором вопросе в каком-либо из разделов (тематик), комментировать вопросы других <b>Пользователей</b>, редактировать\удалять свои вопросы и комментарии, а также изменять свою персональную информацию и может удалить свой профайл. Также <b>Пользователь</b> может помечать комментарии к своим вопросам как ответы. Любой авторизованный <b>Пользователь</b> может ставить оценку (0-10) комментариям и вопросам. На основании этих оценок вычисляется рейтинг каждого из вопросов и комментариев. Рейтинг <b>Пользователя</b> вычисляется на основе рейтингов его вопросов и комментариев.</p>
<p><b>Администратор</b> может создавать\редактировать\удалять\восстанавливать разделы (тематики), удалять\восстанавливать вопросы и комментарии. Также <b>Администратор</b> имеет возможность банить\активировать <b>Пользователей</b>. Под «баном» понимается запрет создания вопросов и комментариев. <b>Администратор</b> может восстановить удаленные пользовательские профайлы. Удалить свой профайл <b>Администратор</b> возможности не имеет.</p>

Диаграмма вариантов использования:
![use case model](https://cloud.githubusercontent.com/assets/13800212/18851366/4e3fcdd0-8444-11e6-92ba-da9abc18447d.png)

Модель БД:
![schema](https://cloud.githubusercontent.com/assets/13800212/18850713/5f03c746-8441-11e6-9e76-57ad36e46dbe.png)