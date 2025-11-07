import React from 'react';
import ChatBox from './ChatBox';
import AdminPanel from './AdminPanel';

export default function App() {
  return (
    <div className="app">
      <h1>Simple Chatbot (Intent + Retrieval)</h1>
      <div className="container">
        <ChatBox />
        <AdminPanel />
      </div>
    </div>
  );
}
