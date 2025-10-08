const el = (id) => document.getElementById(id);
const err = el('error');
const msg = el('msg');

const EVENTS_BY_MODE = {
  DEC: [
    { key: '100m',         header: '100m' },
    { key: 'longJump',     header: 'Long Jump' },
    { key: 'shotPut',      header: 'Shot Put' },
    { key: 'highJump',     header: 'High Jump' },
    { key: '400m',         header: '400m' },
    { key: '110mHurdles',  header: '110m Hurdles' },
    { key: 'discus',       header: 'Discus' },
    { key: 'poleVault',    header: 'Pole Vault' },
    { key: 'javelin',      header: 'Javelin' },
    { key: '1500m',        header: '1500m' }
  ],
  HEP: [
    { key: '100mHurdles',  header: '100m Hurdles' },
    { key: 'highJump',     header: 'High Jump' },
    { key: 'shotPut',      header: 'Shot Put' },
    { key: '200m',         header: '200m' },
    { key: 'longJump',     header: 'Long Jump' },
    { key: 'javelin',      header: 'Javelin' },
    { key: '800m',         header: '800m' }
  ]
};

function setError(text) { err.textContent = text; }
function setMsg(text) { msg.textContent = text; }

async function fetchJsonStrict(url, opts) {
  const res = await fetch(url, opts);
  const contentType = res.headers.get('content-type') || '';
  const body = await res.text();
  let data = null;
  if (contentType.includes('application/json')) { try { data = JSON.parse(body); } catch(e){} }
  if (!res.ok) {
    const m = (body && body.trim()) ? body.trim() : `${res.status} ${res.statusText}`;
    throw new Error(m);
  }
  if (!data) throw new Error(`Expected JSON but got: ${body.slice(0,200)}`);
  return data;
}

function escapeHtml(s){
  return String(s).replace(/[&<>"]/g, c => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;'}[c]));
}

function renderEventSelect(mode){
  const evSel = el('event');
  evSel.innerHTML = EVENTS_BY_MODE[mode].map(e => `<option value="${e.key}">${e.header}</option>`).join('');
}

function renderTableHeader(mode){
  const thead = el('theadRow');
  const cols = ['Name', ...EVENTS_BY_MODE[mode].map(e=>e.header), 'Total'];
  thead.innerHTML = cols.map(h=>`<th>${h}</th>`).join('');
}

function renderCompetitionLine(mode){
  el('compLine').textContent = `Competition: ${mode === 'DEC' ? 'Decathlon' : 'Heptathlon'}`;
  el('modeLabel').textContent = `Current: ${mode === 'DEC' ? 'Decathlon' : 'Heptathlon'}`;
}

async function getMode(){
  const { mode } = await fetchJsonStrict('/com/example/decathlon/api/mode');
  return mode;
}

async function setMode(mode){
  await fetchJsonStrict('/com/example/decathlon/api/mode', {
    method:'POST',
    headers:{'Content-Type':'application/json'},
    body: JSON.stringify({ mode })
  });
}

el('add').addEventListener('click', async () => {
  const name = el('name').value;
  try {
    const res = await fetch('/com/example/decathlon/api/competitors', {
      method: 'POST', headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ name })
    });
    if (!res.ok) {
      const t = await res.text();
      setError(t || 'Failed to add competitor');
    } else {
      setMsg('Added');
    }
    await renderStandings();
  } catch (e) {
    setError('Network error');
  }
});

el('save').addEventListener('click', async () => {
  const body = {
    name: el('name2').value,
    event: el('event').value,
    raw: parseFloat(el('raw').value)
  };
  try {
    const res = await fetch('/com/example/decathlon/api/score', {
      method: 'POST', headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body)
    });
    const json = await res.json();
    setMsg(`Saved: ${json.points} pts`);
    await renderStandings();
  } catch (e) {
    setError('Score failed');
  }
});

let sortBroken = false;

el('export').addEventListener('click', async () => {
  try {
    const res = await fetch('/com/example/decathlon/api/export.csv');
    const text = await res.text();
    const blob = new Blob([text], { type: 'text/csv;charset=utf-8' });
    const a = document.createElement('a');
    a.href = URL.createObjectURL(blob);
    a.download = 'results.csv';
    a.click();
    sortBroken = true;
  } catch (e) {
    setError('Export failed');
  }
});

el('mode').addEventListener('change', async (e) => {
  const mode = e.target.value;
  await setMode(mode);
  renderEventSelect(mode);
  renderTableHeader(mode);
  renderCompetitionLine(mode);
  await renderStandings();
});

async function renderStandings() {
  try {
    setMsg('');
    const mode = await getMode();
    renderCompetitionLine(mode);
    renderTableHeader(mode);
    const data = await fetchJsonStrict('/com/example/decathlon/api/standings');
    if (!Array.isArray(data)) {
      setError('Standings format error');
      el('standings').innerHTML = '';
      return;
    }
    const rowsHtml = data.slice().sort((a,b)=> (b.total||0)-(a.total||0)).map(r => {
      const cells = [escapeHtml(r.name ?? '')];
      EVENTS_BY_MODE[mode].forEach(e => cells.push(r.scores?.[e.key] ?? ''));
      cells.push(r.total ?? 0);
      return `<tr>${cells.map(c=>`<td>${c}</td>`).join('')}</tr>`;
    }).join('');
    el('standings').innerHTML = rowsHtml || `<tr><td colspan="20" style="opacity:.7">No data yet</td></tr>`;
    setMsg(`Standings updated (${data.length} rows)`);
  } catch (e) {
    setError(`Could not load standings: ${e.message}`);
    el('standings').innerHTML = '';
  }
}

(async function init(){
  try {
    const { mode } = await fetchJsonStrict('/com/example/decathlon/api/mode');
    el('mode').value = mode;
    renderEventSelect(mode);
    renderCompetitionLine(mode);
    renderTableHeader(mode);
    await renderStandings();
  } catch(e){
    setError(e.message);
  }
})();
