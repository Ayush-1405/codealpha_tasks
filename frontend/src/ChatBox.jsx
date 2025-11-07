import React, { useState, useEffect, useRef } from 'react';

export default function ChatBox() {
  const [messages, setMessages] = useState([
    { from: 'bot', text: 'Hello — load FAQs from Admin or ask a question.' }
  ]);
  const [input, setInput] = useState('');
  const [suggestions, setSuggestions] = useState([]);
  const [allQuestions, setAllQuestions] = useState([]);
  const messagesEndRef = useRef(null);

  useEffect(() => { messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' }); }, [messages]);

  useEffect(() => {
    const fetchQuestions = async () => {
      try {
        const res = await fetch('http://localhost:8080/api/questions');
        const questions = await res.json();
        setAllQuestions(questions);
      } catch (err) {
        console.error('Failed to fetch questions:', err);
      }
    };
    fetchQuestions();
  }, []);

  const handleInputChange = (e) => {
    const value = e.target.value;
    setInput(value);
    if (value) {
      const filtered = allQuestions.filter(q => q.toLowerCase().includes(value.toLowerCase()));
      setSuggestions(filtered);
    } else {
      setSuggestions([]);
    }
  };

  const handleSuggestionClick = (suggestion) => {
    setInput(suggestion);
    setSuggestions([]);
  };

  const send = async () => {
    if (!input.trim()) return;
    const userMsg = input.trim();
    setMessages(m => [...m, { from: 'user', text: userMsg }]);
    setInput('');
    setSuggestions([]);
    try {
      const res = await fetch('http://localhost:8080/chat', {
        method: 'POST',
        headers: { 'Content-Type': 'text/plain' },
        body: userMsg
      });
      const text = await res.text();
      setMessages(m => [...m, { from: 'bot', text: text }]);
    } catch (err) {
      setMessages(m => [...m, { from: 'bot', text: 'Server error: ' + err.message }]);
    }
  };

  const onKey = (e) => { if (e.key === 'Enter') send(); };

  return (
    <div className="chatbox">
      <div className="messages">
        {messages.map((m, i) => (
          <div key={i} className={`message ${m.from}`}>
            <div className="bubble">{m.text}</div>
          </div>
        ))}
        <div ref={messagesEndRef} />
      </div>
      <div className="inputRow">
        <input value={input} onChange={handleInputChange} onKeyDown={onKey} placeholder="Type your question..." />
        <button onClick={send}>Send</button>
      </div>
      {suggestions.length > 0 && (
        <ul className="suggestions">
          {suggestions.map((s, i) => (
            <li key={i} onClick={() => handleSuggestionClick(s)}>{s}</li>
          ))}
        </ul>
      )}
    </div>
  );
}
