
function safeLoadObjects(){
  try {
    const raw=window.localStorage ? localStorage.getItem('tk4_objects') : null;
    return raw ? JSON.parse(raw) : null;
  } catch(e) {
    console.warn('TK4 storage disabled:', e);
    return null;
  }
}
function safeSaveObjects(v){
  try { if(window.localStorage) localStorage.setItem('tk4_objects',JSON.stringify(v)); }
  catch(e){ console.warn('TK4 storage save failed:', e); }
}
window.addEventListener('error', function(e){
  const el=document.getElementById('app');
  if(el && !el.innerHTML){ el.innerHTML='<div style="padding:24px;font-family:sans-serif;background:#fff;color:#111"><h2>Ошибка запуска TK4</h2><p>'+String(e.message||e.error||'Неизвестная ошибка')+'</p></div>'; }
});
const state={
  screen:'main',
  objects:safeLoadObjects()||[
    {address:'Мытищи, ул. Лесная, 12',client:'Сидоров А.В.',type:'Отопление + водоснабжение',status:'В работе',progress:65,paid:450000,left:240000,contract:690000},
    {address:'Королёв, ул. Полевая, 7',client:'Петрова Е.С.',type:'Отопление',status:'Запланирован',progress:0,start:'15.09.2026',contract:600000,paid:0,left:600000},
    {address:'Пушкино, СНТ Берёзка',client:'Иванов М.С.',type:'Тёплый пол',status:'В работе',progress:25,paid:200000,left:600000,contract:800000},
    {address:'Ивантеевка, ул. Южная, 3',client:'Кузнецов О.В.',type:'Отопление + электрика',status:'Подтверждён',progress:0,start:'22.09.2026',contract:500000,paid:0,left:500000}
  ]
};
const app=document.getElementById('app');
function money(n){return new Intl.NumberFormat('ru-RU').format(Number(n||0))+' ₽'}
function nav(){return `<div class="bottom">
<button class="${state.screen==='main'?'home':''}" onclick="go('main')">🏠<span>Главная</span></button>
<button onclick="go('calendar')">🗓️<span>План</span></button>
<button onclick="go('create')"><span class="plus">+</span><span>Добавить</span></button>
<button onclick="toast('Центр уведомлений — следующий этап')">🔔<span>Уведомления</span></button>
<button onclick="go('settings')">👤<span>Профиль</span></button></div>`}
function shell(content){app.innerHTML=`<div class="phone">${content}${nav()}</div><div id="notice" class="notice"></div>`}
function headerMain(){return `<div class="header"><img src="assets/logo.svg" class="logo"><div class="brand">ТЁПЛАЯ КОМПАНИЯ<small>Строим тепло вместе</small></div><div class="spacer"></div><button class="iconbtn">🔔</button></div><div class="hello"><div class="avatar">👨🏻</div><div><p>Добрый день,</p><h1>Игорь Игоревич</h1><p>Руководитель · 10 сентября 2026</p></div></div>`}
function period(){return `<div class="period"><button>Сегодня</button><button>Неделя</button><button class="active">Месяц</button><button>Квартал</button><button>Год</button></div>`}
function renderMain(){shell(`${headerMain()}${period()}<div class="grid2">
<div class="kpi green"><span class="arrow">›</span><div class="label">Оборот</div><div class="value">1 245 000 ₽</div><div class="delta">↑ +12%</div></div>
<div class="kpi blue"><span class="arrow">›</span><div class="label">Прибыль</div><div class="value">425 000 ₽</div><div class="delta">↑ +18%</div></div>
<div class="kpi orange"><span class="arrow">›</span><div class="label">Монтажи</div><div class="value">8</div><div class="delta">↑ +14%</div></div>
<div class="kpi mint" onclick="go('objects')"><span class="arrow">›</span><div class="label">Объекты в работе</div><div class="value">11</div><div class="delta">↑ +22%</div></div></div>
<div class="section"><div class="sectionHead"><h2>Ключевые показатели</h2><span class="link">Этот месяц⌄</span></div><div class="miniGrid">
<div class="mini"><div class="t">Лиды</div><div class="n">42</div></div><div class="mini"><div class="t">Замеры</div><div class="n">20</div></div><div class="mini"><div class="t">Договоры</div><div class="n">11</div></div><div class="mini"><div class="t">Средний чек</div><div class="n">113 000 ₽</div></div><div class="mini"><div class="t">Дебиторка</div><div class="n">320 000 ₽</div></div><div class="mini"><div class="t">Расходы</div><div class="n">820 000 ₽</div></div></div></div>
<div class="section"><div class="sectionHead"><h2>Быстрый доступ</h2></div><div class="apps">
<button class="appbtn" onclick="go('objects')"><span class="ico">🏠</span><b>Объекты</b></button>
<button class="appbtn" onclick="go('installers')"><span class="ico">👥</span><b>Монтажники</b></button>
<button class="appbtn" onclick="toast('Инженер — следующий ролевой контур')"><span class="ico">👷</span><b>Инженеры</b></button>
<button class="appbtn" onclick="toast('Менеджер — следующий ролевой контур')"><span class="ico">🧑‍💼</span><b>Менеджеры</b></button>
<button class="appbtn" onclick="go('finance')"><span class="ico">₽</span><b>Финансы</b></button>
<button class="appbtn" onclick="go('analytics')"><span class="ico">📊</span><b>Аналитика</b></button>
<button class="appbtn" onclick="go('calendar')"><span class="ico">📅</span><b>Календарь</b></button>
<button class="appbtn" onclick="go('settings')"><span class="ico">⚙️</span><b>Настройки</b></button></div></div>
<div class="section"><div class="sectionHead"><h2>Сегодня требует внимания</h2><span class="link">Все уведомления ›</span></div>
<div class="alert"><span class="dot">!</span><p><b>Просрочена задача</b>Объект ул. Лесная, 12 — не выполнена проверка</p></div>
<div class="alert orange"><span class="dot">▥</span><p><b>Низкая конверсия</b>Замеры → Договоры: 20%</p></div>
<div class="alert"><span class="dot">₽</span><p><b>Есть неоплаченные счета</b>Дебиторская задолженность: 320 000 ₽</p></div></div>`)}
function top(title,sub){return `<div class="topbar"><button class="back" onclick="go('main')">‹</button><div class="title"><h1>${title}</h1><p>${sub}</p></div><div class="spacer"></div><button class="iconbtn">⋯</button></div>`}
function renderObjects(){
 let cards=state.objects.map(o=>`<div class="object"><div class="thumb">🏡</div><div><h3>${o.address}</h3><p>Клиент: ${o.client}</p><p>${o.type}</p>${o.status==='В работе'?`<p>Готовность: <b>${o.progress}%</b></p>`:''}</div><div><span class="badge ${o.status==='Запланирован'?'blue':o.status==='Подтверждён'?'orange':''}">${o.status}</span></div></div>`).join('');
 shell(`${top('Объекты','Все объекты компании')}<div class="tabs"><button class="tab active">Все</button><button class="tab">В работе</button><button class="tab">Запланированы</button><button class="tab">Подтверждены</button><button class="tab">Завершены</button></div><div class="summary4"><div class="stat"><span>В работе</span><b>6</b></div><div class="stat"><span>Запланированы</span><b>18</b></div><div class="stat"><span>Подтверждены</span><b>7</b></div><div class="stat"><span>Замеры за месяц</span><b>42</b></div></div><div class="stat" style="margin-top:8px"><span>В монтаж перешли</span><b>11</b></div><div class="search"><input placeholder="Поиск по адресу, клиенту…"><button class="iconbtn" onclick="go('create')">＋</button></div>${cards}`)}
function renderCreate(){shell(`${top('Создать объект','Новый объект в системе')}<div class="sync"><b>🔄 Режим DEMO · API пока не подключён</b><p>Структура готова для синхронизации с Google Sheets 4.0 через Apps Script API.</p></div><form id="createForm" class="formgrid">
<div class="field"><label>Клиент *</label><input name="client" required value="Иванов Сергей Петрович"></div><div class="field"><label>Телефон</label><input name="phone" value="+7 915 123-45-67"></div>
<div class="field full"><label>Адрес объекта *</label><input name="address" required value="Московская обл., г. Химки, ул. Лесная, д. 12"></div>
<div class="field full"><label>Название в списках</label><input value="Химки, ул. Лесная, 12" disabled></div>
<div class="field"><label>Вид работ *</label><select name="type"><option>Комплексное утепление</option><option>Утепление пола</option><option>Фасад</option></select></div>
<div class="field"><label>Сумма договора</label><input name="sum" value="350000"></div>
<div class="field"><label>Дата замера</label><input type="date" name="survey" value="2026-09-10"></div><div class="field"><label>Плановая дата монтажа</label><input type="date" name="plan" value="2026-09-25"></div>
<div class="field"><label>Статус объекта</label><select name="status"><option>Подтверждён клиентом</option><option>Запланирован</option><option>В работе</option></select></div><div class="field"><label>Инженер</label><select><option>Константин</option></select></div>
<div class="field full"><label>Комментарий</label><textarea placeholder="Дополнительная информация"></textarea></div></form>
<div class="actionCard"><h3>📄 Техническое задание <span class="badge orange" style="float:right">Не создано</span></h3><p>ТЗ: монтажники, согласованная сумма каждому, план по дням, объёмы и график оплат клиента.</p><button class="btn outline" onclick="toast('Редактор ТЗ — следующий технический этап')">Сделать техническое задание</button></div>
<div class="actionCard"><h3>👥 Монтажники на объекте</h3><p>Назначаются конкретные сотрудники, а не фиксированная бригада.</p><button class="btn outline" onclick="go('installers')">Назначить монтажников</button></div>
<div class="bottomActions"><button class="btn" onclick="go('objects')">Отмена</button><button class="btn primary" onclick="saveObject(event)">Сохранить объект</button></div>`)}
function renderInstallers(){
 const rows=[['Алексей Смирнов','22','8','4',92000,30000],['Илья Орлов','20','10','3',94000,20000],['Сергей Плотников','18','12','2',71000,15000]];
 shell(`${top('Монтажники','Аналитика, выплаты, инструмент')}${period()}<div class="grid2"><div class="kpi orange"><div class="label">Монтажников</div><div class="value">8</div></div><div class="kpi mint"><div class="label">На объектах сегодня</div><div class="value">6</div></div><div class="kpi blue"><div class="label">Закрыто объектов</div><div class="value">14</div></div><div class="kpi orange"><div class="label">К выдаче</div><div class="value">344 000 ₽</div></div></div>
<div class="section"><div class="miniGrid"><div class="mini"><div class="t">Начислено</div><div class="n">524 000 ₽</div></div><div class="mini"><div class="t">Выдано / аванс</div><div class="n">180 000 ₽</div></div><div class="mini"><div class="t">Осталось</div><div class="n">344 000 ₽</div></div></div></div>
<div class="section"><div class="sectionHead"><h2>Сотрудники</h2><span class="link">Вся аналитика ›</span></div>${rows.map(r=>`<div class="installer"><div class="avatar">👷</div><div><h3>${r[0]}</h3><div class="meta">${r[1]} раб. дней · ${r[2]} выходных · ${r[3]} объекта</div><div class="meta">Инструмент: 3 ед. · Форма: учёт выдачи</div></div><div class="money">К выдаче<br>${money(r[4]-r[5])}</div></div>`).join('')}</div>`)}
function renderFinance(){
 shell(`${top('Финансы','Приходы, расходы и живые остатки')}${period()}<div class="grid2">
<div class="kpi green"><div class="label">Оборот / приходы</div><div class="value">2 480 000 ₽</div><div class="delta">↑ +14%</div></div>
<div class="kpi orange"><div class="label">Расходы</div><div class="value">1 860 000 ₽</div><div class="delta" style="color:var(--red)">↑ +8%</div></div>
<div class="kpi blue"><div class="label">Общий остаток</div><div class="value">1 320 000 ₽</div></div>
<div class="kpi mint"><div class="label">Подотчёт</div><div class="value">806 000 ₽</div></div></div>
<div class="section"><div class="sectionHead"><h2>Остатки у подотчётных лиц</h2><span class="link">Все ›</span></div><div class="grid2">
<div class="personBalance"><div class="avatar" style="width:42px;height:42px">👨🏻</div><div><b>Игорь</b><div class="meta">Текущий остаток</div></div><b>420 000 ₽</b></div>
<div class="personBalance"><div class="avatar" style="width:42px;height:42px">👨🏻</div><div><b>Константин</b><div class="meta">Текущий остаток</div></div><b>386 000 ₽</b></div></div></div>
<div class="bigActions"><button onclick="toast('Форма прихода')">➕ Приход</button><button onclick="toast('Форма расхода')">➖ Расход</button><button onclick="toast('Внутренний перевод')">↔ Передать</button></div>
<div class="section"><div class="sectionHead"><h2>Операции</h2><span class="link">Этот месяц⌄</span></div>
<div class="filterRow"><button class="chip active">Все</button><button class="chip">Приходы</button><button class="chip">Расходы</button><button class="chip">Переводы</button><button class="chip">Объект</button><button class="chip">Монтажник</button><button class="chip">Маркетинг</button></div>
<div class="stat">
<div class="tx"><span class="txIco in">↓</span><div><h4>Оплата по договору №125</h4><p>Объект: ул. Лесная, 12</p></div><span class="amt" style="color:var(--green)">+350 000 ₽</span></div>
<div class="tx"><span class="txIco out">↑</span><div><h4>Покупка материалов</h4><p>Объект: ул. Садовая, 8</p></div><span class="amt" style="color:var(--red)">−125 000 ₽</span></div>
<div class="tx"><span class="txIco out">↑</span><div><h4>Аванс монтажнику</h4><p>Сергей Петров</p></div><span class="amt" style="color:var(--red)">−80 000 ₽</span></div>
<div class="tx"><span class="txIco move">↔</span><div><h4>Константин → Игорь</h4><p>Внутренний перевод</p></div><span class="amt">50 000 ₽</span></div>
</div></div>
<div class="section"><div class="sectionHead"><h2>Требует внимания</h2></div><div class="alert"><span class="dot">!</span><p><b>Приход без получателя</b>Нужно указать подотчётное лицо</p></div><div class="alert orange"><span class="dot">!</span><p><b>Расход без объекта</b>Проверьте назначение расхода</p></div></div>`)}
function renderAnalytics(){
 const rows=[
 {a:'Химки, ул. Лесная, 12',c:'Иванов А. В.',s:'В работе',contract:1200000,paid:800000,plan:400000,debt:0,next:'200 000 ₽ — 25.09.2026',p:67},
 {a:'Мытищи, ул. Центральная, 8',c:'ООО СТРОЙ-ПЛЮС',s:'Запланирован',contract:950000,paid:0,plan:950000,debt:0,next:'180 000 ₽ — 12.09.2026',p:0},
 {a:'Королёв, ул. Полевая, 7',c:'Петров С. М.',s:'Завершён',contract:600000,paid:400000,plan:0,debt:200000,next:'Просрочка 4 дня',p:100}
 ];
 shell(`${top('Аналитика','Объекты, поступления, дебиторка')}${period()}<div class="grid2">
<div class="kpi green"><div class="label">Оборот</div><div class="value">2 480 000 ₽</div></div>
<div class="kpi orange"><div class="label">Дебиторка</div><div class="value">680 000 ₽</div></div>
<div class="kpi blue"><div class="label">План поступлений</div><div class="value">1 120 000 ₽</div></div>
<div class="kpi mint"><div class="label">Осталось получить</div><div class="value">1 800 000 ₽</div></div></div>
<div class="section"><div class="sectionHead"><h2>Фильтры</h2><span class="link">Этот месяц⌄</span></div><div class="filterRow"><button class="chip active">Все объекты</button><button class="chip">В работе</button><button class="chip">Запланированы</button><button class="chip">Завершены</button><button class="chip">По инженеру</button><button class="chip">По менеджеру</button></div></div>
<div class="section"><div class="sectionHead"><h2>Аналитика по объектам</h2><span class="link">${rows.length} объекта ›</span></div>
${rows.map(r=>`<div class="objAnalytics"><h3>${r.a} <span class="badge" style="float:right">${r.s}</span></h3><p style="font-size:10px;color:var(--muted);margin:0 0 8px">${r.c}</p><div class="metricLine"><div>Договор<b>${money(r.contract)}</b></div><div>Получено<b>${money(r.paid)}</b></div><div>План поступлений<b>${money(r.plan)}</b></div><div>Дебиторка<b style="${r.debt?'color:var(--red)':''}">${money(r.debt)}</b></div></div><div class="progress"><i style="width:${r.p}%"></i></div><p style="font-size:9px;margin:5px 0 0;color:${r.debt?'var(--red)':'var(--muted)'}">Следующий платёж: ${r.next}</p></div>`).join('')}</div>
<div class="section"><div class="sectionHead"><h2>Требует внимания</h2></div><div class="alert"><span class="dot">!</span><p><b>Просрочен платёж по объекту — 200 000 ₽</b>Королёв, ул. Полевая, 7</p></div><div class="alert orange"><span class="dot">⏱</span><p><b>Через 2 дня ожидается платёж — 180 000 ₽</b>Мытищи, ул. Центральная, 8</p></div></div>`)}
function renderCalendar(){shell(`${top('Календарь','Загрузка монтажников и объектов')}<div class="placeholder"><h2>Календарь 4.0</h2><p>Следующий этап: день / неделя / месяц, даты из ТЗ, конкретные монтажники и предупреждение о двойном назначении.</p></div>`)}
function renderSettings(){shell(`${top('Настройки','Профиль и приложение')}<div class="actionCard"><h3>👤 Профиль</h3><p>Фото из галереи/камеры, ФИО, телефон, должность. Роль назначает администратор.</p><button class="btn outline" onclick="toast('Фото профиля — следующий этап')">Изменить фото</button></div><div class="actionCard"><h3>🌗 Тема</h3><p>Светлая / Тёмная / Как на устройстве.</p><div class="filterRow"><button class="chip active">Светлая</button><button class="chip">Тёмная</button><button class="chip">Система</button></div></div><div class="actionCard"><h3>🔔 Уведомления</h3><p>Объекты, ТЗ, платежи, отчёты, просрочки.</p></div><div class="actionCard"><h3>ℹ️ Версия</h3><p>${(window.TK4_CONFIG && window.TK4_CONFIG.version) || '4.0'}</p></div>`)}
function go(s){state.screen=s;render();window.scrollTo(0,0)}
function toast(t){const n=document.getElementById('notice');if(!n)return;n.textContent=t;n.classList.add('show');setTimeout(()=>n.classList.remove('show'),1800)}
function saveObject(e){e.preventDefault();const f=document.getElementById('createForm');const d=new FormData(f);state.objects.unshift({address:d.get('address'),client:d.get('client'),type:d.get('type'),status:d.get('status'),progress:0,start:d.get('plan'),contract:Number(d.get('sum')||0),paid:0,left:Number(d.get('sum')||0)});safeSaveObjects(state.objects);go('objects');setTimeout(()=>toast('Объект сохранён локально (DEMO)'),50)}
function render(){({main:renderMain,objects:renderObjects,create:renderCreate,installers:renderInstallers,finance:renderFinance,analytics:renderAnalytics,calendar:renderCalendar,settings:renderSettings}[state.screen]||renderMain)()}
render();
(function(){
  var boot=document.getElementById('boot');
  if(boot){boot.style.display='none';}
})();
