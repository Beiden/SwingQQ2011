�����й��ܡ�
1������˿�������
2���ͻ��˸����û�����������е�¼
3���򵥵Ļ���(Ч�����Ǻܺ�)
4��һ��һ��������(�����ص�bug)
5����ͼ����
�����ݿ�˵����
���ݿ�ʹ�õ���smallsql;

���ݿ�ʹ�ã�

��smallsql.jar�ŵ������ݿ�(qqdb)ͬһ��Ŀ¼,  java -jar smallsql.jar.
��dos�����£����� USE qqdb  �س�����
select * from userinfos  �س�����
select * from users �س�����
select * from friend �س�����

�������������ģ�����Ƶĺܼ򵥣����ڲ�3�ű��û���users���û���Ϣ��userinfos�����ѱ�friend,����ֶο��Բο�user.hbm.xml��userinfo.hbm.xml��friend.hbm.xml��
ʹ��hibernate�����ݿ���з���;
���ṹ˵����
��Ŀ�ļ���˵��:
|--src Դ����
   |--org.fw һЩ�����ؼ�
   |--org.fw.cellrender ��Ⱦ
   |--org.fw.data JList�ȿؼ���ʹ�õ�������
   |--org.fw.db.pojo hibernate�õ���pojo��
   |--org.fw.event �Զ����¼���
   |--org.fw.image ��ȡͼƬ
   |--org.fw.manager RepaintManager
   |--org.fw.qq qq�ͻ��������
   |--org.fw.qq.plugins.screencut ��ͼ���
   |--org.fw.qq.server qq����������
   |--org.fw.test һЩ������
   |--org.fw.utils һЩ������
|--cfg ��Դ�����ļ�
|--info smallsqlʹ�õ�һЩ��ͼ
|--lib 
|--qqdb ϵͳ���ݿ�
|--skin �õ������ͼƬ

��ԭʼ���ݡ�
�������ģ�����ע����Ӻ�����Щ��ûŪ,Ҫ����µ����ݾ���sql�������Ӱɡ�

�����˺�:786074249,914001405,442714254,5201314 ���붼��123456

���������衿
������org.fw.qq.server��QQServer��������org.fw.qq��Main��

���������⡿��
��������ֵģ�����ûʲô�ƻ����Ϳ�ʼ��д�ˡ���һ��дһ�㣬�����淢����д��Ҳ��ú������ˡ�
������ػ���ںܴ�����⣻
�ܶ�ϸ�ڷ��滹���ںܴ�����⣻
������������Ƶ���죬ֻ�Ǽ򵥵������³��ԣ�����������������������塣

