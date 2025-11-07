import React, { useState } from 'react';

export default function AdminPanel() {
  const [file, setFile] = useState(null);
  const [status, setStatus] = useState('');

  const upload = async () => {
    if (!file) { setStatus('Choose a file first'); return; }
    const fd = new FormData();
    fd.append('file', file);
    setStatus('Uploading...');
    try {
      const res = await fetch('http://localhost:8080/api/train', { method: 'POST', body: fd });
      const j = await res.json();
      if (res.ok) {
        setStatus('Trained: ' + (j.faqs || 0) + ' FAQs loaded.');
      } else setStatus('Error: ' + (j.error || JSON.stringify(j)));
    } catch (err) {
      setStatus('Upload failed: ' + err.message);
    }
  };

  return (
    <div className="admin">
      <h2>Admin</h2>
      <input type="file" accept=".txt,.csv" onChange={e => setFile(e.target.files[0])} />
      <button onClick={upload}>Upload & Train</button>
      <div className="status">{status}</div>
      <div className="hint">Use pipe `|` separated file: question|answer|category</div>
    </div>
  );
}
